package repository;

import domain.entities.BaseEntity;
import domain.validators.Validator;
import domain.validators.ValidatorException;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class GenericDataBaseRepository<Type extends BaseEntity<Long>>
        extends SortingRepository<Long, Type> {
    private final String className;
    private final String tableName;
    private Connection connection;

    public GenericDataBaseRepository(Validator<Type> validator,
                                     String host, String password, String user, String dataBaseName, String tableName,
                                     String className) {
        super(validator);
        this.className = className;
        this.tableName = tableName;

        try {
            this.connection = DriverManager.getConnection(
                    String.format("jdbc:postgresql://%s/%s", host, dataBaseName), user, password);
            System.out.println("Connection to " + this.tableName + " table established.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.loadData();
    }

    private void loadData() {
        try {
            Statement statement = this.connection.createStatement();
            ResultSet table = statement.executeQuery("select * from " + this.tableName);
            while (table.next()) {
                ArrayList<String> row = new ArrayList<>();
                for(int index = 1; true;index += 1) {
                    try { row.add(table.getString(index));
                    } catch (SQLException stop) { break; }
                }
                String rowString = row.stream().reduce("",
                        (first, second) -> first + "," + second);
                rowString = rowString.substring(1);

                Type instance = null;
                try {
                    //instance = (Type) this.clazz.getConstructor().newInstance(line);
                    instance = (Type) Class.forName(this.className).getConstructor(String.class).newInstance(rowString);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e){
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                try{
                    super.save(instance);
                } catch (ValidatorException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException ignored) {}
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<Type> save(Type entity) throws ValidatorException {
        Optional<Type> optional = super.save(entity);
        if (optional.isPresent()) {
            return optional;
        }
        this.saveToDataBase(entity);
        return Optional.empty();
    }

    private void saveToDataBase(Type entity) {
        try {
            String values = entity.toString(",");
            values = "('" + values.replace(",", "','") + "')";
            //System.out.println("\tExecuting\ninsert into " + this.tableName + " values " + values + ";");

            PreparedStatement statement = this.connection.prepareStatement(
                    "insert into " + this.tableName + " values " + values + ";");
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Type> delete(Long ID) {
        try {
            //System.out.println("\tExecuting\ndelete from " + this.tableName + " where ID = " + ID);
            PreparedStatement statement = this.connection.prepareStatement(
                    "delete from " + this.tableName + " where ID = " + ID);
            statement.executeUpdate();

            //Statement statement = this.connection.createStatement();
            //statement.executeQuery("delete from " + this.tableName + " where ID = " + ID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return super.delete(ID);
    }

    @Override
    public Optional<Type> update(Type entity) throws ValidatorException {
        this.delete(entity.getId());
        return this.save(entity);
    }

    public void close() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

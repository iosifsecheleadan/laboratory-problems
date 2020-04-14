package repository.spring;

import domain.entities.BaseEntity;
import domain.validators.Validator;

import domain.validators.ValidatorException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import repository.sort.SortingRepository;

import java.util.List;
import java.util.Optional;

public class GenericJDBCTemplateRepository<Type extends BaseEntity<Long>>
    extends SortingRepository<Long, Type> {
    private final String className;
    private final String tableName;
    private JdbcOperations jdbcOperations;

    public GenericJDBCTemplateRepository(Validator<Type> validator,
                                         String host, String password, String user, String dataBaseName, String tableName,
                                         String className) {
        super(validator);
        this.className = className;
        this.tableName = tableName;
        this.setOperations(host, password, user, dataBaseName);
        this.loadData();
    }

    private void setOperations(String host, String password, String user, String dataBaseName) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(String.format("jdbc:postgresql://%s/%s", host, dataBaseName));
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        dataSource.setDriverClassName("org.postgresql.Driver");

        JdbcTemplate template = new JdbcTemplate();
        template.setDataSource(dataSource);

        this.jdbcOperations = template;
    }

    private void loadData() {
        try {
            List<Type> list = this.jdbcOperations.query("select * from " + this.tableName,
                    new BeanPropertyRowMapper<Type>((Class<Type>) Class.forName(this.className)));
            list.forEach(super::save);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Type> save (Type entity) throws ValidatorException {
        Optional<Type> optional = super.save(entity);
        if (optional.isPresent()) {
            return optional;
        }
        this.saveToDataBase(entity);
        return Optional.empty();
    }

    private void saveToDataBase(Type entity) {
        String values = entity.toString(",");
        values = "('" + values.replaceAll(",", "','") + "')";
        this.jdbcOperations.update("insert into " + this.tableName + " values " + values + ";");
    }

    @Override
    public  Optional<Type> delete(Long ID) {
        this.jdbcOperations.update("delete from " + this.tableName + " where ID = " + ID);
        return super.delete(ID);
    }

    @Override
    public Optional<Type> update(Type entity) throws ValidatorException {
        this.delete(entity.getId());
        return this.save(entity);
    }
}

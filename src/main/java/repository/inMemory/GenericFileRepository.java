package repository;

import domain.entities.BaseEntity;
import domain.validators.Validator;
import domain.validators.ValidatorException;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author sechelea
 * @author vinczi
 * @param <Type> generic Type
 */
public class GenericFileRepository<Type extends BaseEntity<Long>>
        extends InMemoryRepository<Long, Type> {
    private final String fileName;
    private final String className;

    public GenericFileRepository(Validator<Type> validator,
                                 String fileName,
                                 String className) {
        super(validator);
        this.fileName = fileName;
        this.className = className;
        this.loadData();
    }

    private void loadData() {
        Path path = Paths.get(fileName);
        try {
            Files.lines(path).forEach(line -> {
                Type instance = null;
                try {
                    //instance = (Type) this.clazz.getConstructor().newInstance(line);
                    instance = (Type) Class.forName(this.className).getConstructor(String.class).newInstance(line);
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
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Type> save(Type entity) throws ValidatorException {
        Optional<Type> optional = super.save(entity);
        if (optional.isPresent()) {
            return optional;
        }
        this.saveToFile(entity);
        return Optional.empty();
    }

    private void saveToFile(Type entity) {
        Path path = Paths.get(this.fileName);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)){
            bufferedWriter.write(entity.toString(","));
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Type> delete(Long ID) {
        try {
            File inputFile = new File(fileName);
            File tempFile = new File("tempFile");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                // trim newline when comparing with lineToRemove
                currentLine = currentLine.trim();
                List<String> values = Arrays.asList(currentLine.split(","));
                String id = values.get(0);
                if (id.equals(Long.toString(ID))) continue;
                writer.write(currentLine + System.getProperty("line.separator"));
            }
            writer.close();
            reader.close();
            boolean successful = tempFile.renameTo(inputFile);
        } catch(Exception e){
            e.printStackTrace();
        }

        return super.delete(ID);
    }

    @Override
    public Optional<Type> update(Type entity) throws ValidatorException {
        try {
            File inputFile = new File(fileName);
            File tempFile = new File("tempFile");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;
            Long ID = entity.getId();

            while ((currentLine = reader.readLine()) != null) {
                // trim newline when comparing with lineToRemove
                currentLine = currentLine.trim();
                List<String> values = Arrays.asList(currentLine.split(","));
                String id = values.get(0);
                if (id.equals(Long.toString(ID))) {
                    writer.write(entity.toString(",") + System.getProperty("line.separator"));
                    continue;
                }
                writer.write(currentLine + System.getProperty("line.separator"));
            }
            writer.close();
            reader.close();
            boolean successful = tempFile.renameTo(inputFile);
        } catch(Exception e){
            e.printStackTrace();
        }

        return super.update(entity);
    }
}

package com.luongnv.practice.hibernate;

import com.luongnv.practice.hibernate.annotation.Column;
import com.luongnv.practice.hibernate.annotation.PrimaryKey;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class Hibernate<T> {

    Connection con;

    private Hibernate() throws SQLException {
        this.con = DriverManager.getConnection("jdbc:h2:./Database", "sa", "");
    }

    public static <T> Hibernate<T> getConnection() throws SQLException {
        return new Hibernate<>();
    }

    public void write(T entity) throws IllegalAccessException, SQLException {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }
        Field pkey = null;
        List<Field> columns = new ArrayList<>();
        StringJoiner joiner = new StringJoiner(",");

        Class<?> clss = entity.getClass();
        for (Field field : clss.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(PrimaryKey.class)) {
                pkey = field;
                joiner.add(field.getName());
                System.out.println("Primary Key is : " + field.getName() + " --- value is: " + field.get(entity));
            } else if (field.isAnnotationPresent(Column.class)) {
                joiner.add(field.getName().toLowerCase());
                columns.add(field);

                System.out.println("The column " + field.getName() + ": " + field.get(entity));
            }
        }

        int number = columns.size(); // 1 for pkey
        String questionMark = IntStream.range(0, number)
                .mapToObj(e -> "?")
                .collect(Collectors.joining(","));

        System.out.println("Question mark : " + questionMark);

        String sql = "insert into " + clss.getSimpleName().toLowerCase() + "(" + joiner + ") values(" + questionMark + ")";

        System.out.println("sql : " + sql);

        PreparedStatement stmt = con.prepareStatement(sql);
        if (pkey.getType() == long.class) {
            stmt.setLong(1, pkey.getLong(entity));
        }

        int index = 2;
        for (Field field : columns) {
            if (field.getType() == int.class) {
                stmt.setInt(index++, field.getInt(entity));
            } else if (field.getType() == String.class) {
                stmt.setString(index++, field.get(entity).toString());
            }
        }

        stmt.executeUpdate();
    }

    public T read(Class<T> tClass, long transactionId) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Field[] fields = tClass.getDeclaredFields();

        Field pkey = null;
        for (Field field : fields) {
            if (field.isAnnotationPresent(PrimaryKey.class)) {
                field.setAccessible(true);
                pkey = field;
                break;
            }
        }

        String sql = "select * from " + tClass.getSimpleName().toLowerCase() + " where id = ? ";
        PreparedStatement stmt = con.prepareStatement(sql);
        if (pkey != null
                && pkey.getType() == long.class) {
            stmt.setLong(1, transactionId);
        }

        ResultSet resultSet = stmt.executeQuery();

        Constructor<T> entityConstructor = tClass.getConstructor();
        T entity = entityConstructor.newInstance();

        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)
                    || field.isAnnotationPresent(PrimaryKey.class)) {
                field.setAccessible(true);
                if (field.getType() == String.class) {
                    String value = resultSet.getString(field.getName());
                    field.set(entity, value);
                } else if (field.getType() == int.class) {
                    int value = resultSet.getInt(field.getName());
                    field.set(entity, value);
                } else {
                    long value = resultSet.getLong(field.getName());
                    field.set(entity, value);
                }
            }
        }

        return entity;
    }
}

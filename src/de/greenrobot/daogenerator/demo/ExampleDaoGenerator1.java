/*
 * Copyright (C) 2011 Markus Junginger, greenrobot (http://greenrobot.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.greenrobot.daogenerator.demo;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

/**
 * Generates entities and DAOs for the example project DaoExample.
 * 
 * Run it as a Java application (not Android).
 * 
 * @author Markus
 */
public class ExampleDaoGenerator1 {

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, "com.qing.dao");
        schema.enableKeepSectionsByDefault();
        schema.enableChangeName(false);
//        schema.setAllName2UpperCase(false);
//        schema.setSuperclassOfAll("de.greenrobot.dao.BaseModel");
        
        Entity user = schema.addEntity("User");//参与者
        user.addIdProperty();
        user.addStringProperty("name");
        user.addStringProperty("nick");
        user.addDateProperty("date");
        
        Entity userRecord = schema.addEntity("UserRecord");//参与者
        userRecord.addIdProperty();
        Property uid = userRecord.addLongProperty("userId").getProperty();
        Property rid = userRecord.addLongProperty("recordId").getProperty();
        userRecord.addDoubleProperty("cost");
        userRecord.addDateProperty("dateTime");
        
        Entity record = schema.addEntity("Record");//消费记录
        record.addIdProperty();
        record.addDateProperty("dateTime");
        Property payer = record.addLongProperty("payer").getProperty();
        record.addDoubleProperty("amount");
        record.addDoubleProperty("avg");
        record.addStringProperty("description");
        
        userRecord.addToOne(user, uid);
        userRecord.addToOne(record, rid);
        record.addToOne(user, payer);
        record.addToMany(userRecord, rid);
        

        new DaoGenerator().generateAll(schema, "../AA/src");
    }

    private static void addNote(Schema schema) {
        Entity note = schema.addEntity("People");
        note.addIdProperty();
        note.addStringProperty("name").notNull();
        note.addStringProperty("address");
        note.addDateProperty("birthday");
    }

    private static void addCustomerOrder(Schema schema) {
        Entity teacher = schema.addEntity("Teacher");
        teacher.addIdProperty();
        teacher.addStringProperty("name").notNull();

        Entity student = schema.addEntity("Student");
        student.setTableName("student"); // "ORDER" is a reserved keyword
        student.addIdProperty();
        student.addShortProperty("sex");
        student.addStringProperty("name").notNull();
        student.addDateProperty("birthday").getProperty();
        Property teacherId = student.addLongProperty("teacherId").notNull().getProperty();
        student.addToOne(teacher, teacherId);
        
        ToMany teacherToStudent = teacher.addToMany(student, teacherId);
        teacherToStudent.setName("teacher_student");
//        teacherToStudent.orderAsc(orderDate);
    }
    
    
    /*public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1000, "de.greenrobot.daoexample");

        addNote(schema);
        addCustomerOrder(schema);

        new DaoGenerator().generateAll(schema, "../DaoExample/src-gen");
    }

    private static void addNote(Schema schema) {
        Entity note = schema.addEntity("Note");
        note.addIdProperty();
        note.addStringProperty("text").notNull();
        note.addStringProperty("comment");
        note.addDateProperty("date");
    }

    private static void addCustomerOrder(Schema schema) {
        Entity customer = schema.addEntity("Customer");
        customer.addIdProperty();
        customer.addStringProperty("name").notNull();

        Entity order = schema.addEntity("Order");
        order.setTableName("ORDERS"); // "ORDER" is a reserved keyword
        order.addIdProperty();
        Property orderDate = order.addDateProperty("date").getProperty();
        Property customerId = order.addLongProperty("customerId").notNull().getProperty();
        order.addToOne(customer, customerId);

        ToMany customerToOrders = customer.addToMany(order, customerId);
        customerToOrders.setName("orders");
        customerToOrders.orderAsc(orderDate);
    }*/

}

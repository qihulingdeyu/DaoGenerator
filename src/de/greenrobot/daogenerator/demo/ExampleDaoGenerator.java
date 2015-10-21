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
public class ExampleDaoGenerator {

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(2, "cn.poco.dao");
        schema.enableKeepSectionsByDefault();
        schema.enableChangeName(false);
//        schema.setAllName2UpperCase(false);
//        schema.setSuperclassOfAll("de.greenrobot.dao.BaseModel");
        
        Entity ttype = schema.addEntity("RType");
//        ttype.setSuperclass("de.greenrobot.dao.BaseModel");
        ttype.setTableName("res_type");
        ttype.addIdProperty().primaryKey().autoincrement();
        ttype.addStringProperty("en_name").notNull();
        ttype.addStringProperty("zh_name").notNull();
       
        Entity theme = schema.addEntity("Theme");
//        theme.setSuperclass("de.greenrobot.dao.BaseModel");
        theme.setTableName("theme");
        theme.addLongProperty("id").primaryKey();
        theme.addStringProperty("file_tracking_id").notNull();
        theme.addStringProperty("name").notNull();
        theme.addIntProperty("type");
        
        Property restype = theme.addStringProperty("restype").notNull().getProperty();
        Property restype_id = theme.addLongProperty("restype_id").getProperty();
        
        theme.addIntProperty("order");
        theme.addIntProperty("tracking_code");
        theme.addStringProperty("tags");
        theme.addStringProperty("thumb_80");
        theme.addStringProperty("thumb_120");
        theme.addIntProperty("size");
        
        
        Entity res_arr = schema.addEntity("ResArr");
//        res_arr.setSuperclass("de.greenrobot.dao.BaseModel");
        res_arr.setTableName("res_arr");
        res_arr.addIdProperty().autoincrement();
        res_arr.addStringProperty("info");
        res_arr.addStringProperty("proportion");
        res_arr.addStringProperty("maxPicNum");
        res_arr.addStringProperty("minPicNum");
        Property themeId = res_arr.addLongProperty("themeId").getProperty();
        res_arr.addIntProperty("count");
        
//        /**
//         * 修改表结构
//         * @param db
//         */
//        public void alterTable(SQLiteDatabase db){
//        	//ALTER TABLE COMPANY RENAME TO OLD_COMPANY;//重命名字段
//        	//ALTER TABLE OLD_COMPANY ADD COLUMN SEX char(1);//添加新的字段
//        	db.execSQL("ALTER TABLE "+ResArrDao.TABLENAME+" ADD COLUMN "+ResArrDao.Properties.Count.name+" integer(11);");
//        }
        
        res_arr.addToOne(theme, themeId);
        theme.addToMany(res_arr, themeId);
        
        theme.addToOne(ttype, restype_id);
        ttype.addToMany(theme, restype_id);
        
//        addNote(schema);
//        addCustomerOrder(schema);

        new DaoGenerator().generateAll(schema, "JavaTest/src-gen");
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

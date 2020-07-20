package com.montoya.appsqlite.modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.montoya.appsqlite.entidad.Persona;
import com.montoya.appsqlite.util.BDPersona;
import com.montoya.appsqlite.util.ConstantesBD;

import java.util.ArrayList;

public class PersonaDAO {
    BDPersona bdPersona;
    SQLiteDatabase database;

    public PersonaDAO(Context c) {
        bdPersona=new BDPersona(c);
    }
    public  void openBD(){
        database=bdPersona.getWritableDatabase();
    }
    public  void closeBD(){
        bdPersona.close();
        database.close();
    }
    public  void registrarPersona(Persona p){
        try{
            ContentValues values=new ContentValues();
            values.put("dni",p.getDni());
            values.put("ape",p.getApe());
            values.put("nom",p.getNom());
            values.put("email",p.getEmail());
            database.insert(ConstantesBD.NOMBRETABLA,null,values);
        }catch (Exception e){

        }
    }
 public  void modificarDatos(Persona p){
        try {
            ContentValues values=new ContentValues();
            values.put("dni",p.getDni());
            values.put("ape",p.getApe());
            values.put("nom",p.getNom());
            values.put("email",p.getEmail());
            //database.update(ConstantesBD.NOMBRETABLA,values,"id=?",
              //      new String[]{p.getId()+""} );
 database.update(ConstantesBD.NOMBRETABLA,values,"id="+p.getId(),null);
        }catch (Exception e){

        }
 }
 public  void eliminarPersona(int id){
        try {
            database.delete(ConstantesBD.NOMBRETABLA,"id="+id,null);
        }catch (Exception e){

        }
 }
 public ArrayList<Persona> getPersonas(){
        ArrayList<Persona> listaPer=new ArrayList<>();
        try {
            Cursor c=database.rawQuery("SELECT * FROM "+ConstantesBD.NOMBRETABLA,
                                                                     null);
            while (c.moveToNext()){
                //public Persona(int id, int dni, String ape, String nom, String email) {
           listaPer.add(new Persona(c.getInt(0), c.getInt(1),
           c.getString(2),c.getString(3),c.getString(4)));
            }

        }catch (Exception e){
          return  null;
        }
        return listaPer;
 }

}

package com.words_ver_0_1;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DB_Manager {
	
		SQLiteDatabase db;
		int size_Words = 20;	//�ѹ��� �ܿ� �ܾ� ��
		int i;	//Words �� ������ �� �ӽ� int
		
		DB_Manager (SQLiteDatabase db){
			
			this.db = db;
			
		}
		
		public void insert_Words (String name, String past, String pronun, String mean, int level){
			
			db.execSQL("INSERT INTO Words VALUES (null, '" + name + "', '" + past + "', '" + pronun + "', '" +
			mean + "', " + level + ");");			
	
		}
		
		public void insert_All_Words (String name, String past, String pronun, String mean){
			
			db.execSQL("INSERT INTO All_Words VALUES (null, '" + name + "', '" + past + "', '" + pronun + "', '" +
			mean + "', 1);");
			
		}
		
		public void delete_Words (){
			
		}
		
		public void fill_Words (){
			/*All_Words�κ��� �ܾ ������ Words�� ä������*/
			Cursor cursor = db.rawQuery("SELECT name, past, pronun, mean, taken FROM All_Words", null);
			i = 0;
			
			//�ϳ��� ��Ʈ���� �ְ� ���
			while ( cursor.moveToNext() && (i < size_Words) ) {
				
				/*boolean�� true�� ���ڵ忡 ���ؼ��� ������*/
				if(cursor.getInt(4) != 0){
					
					String name = cursor.getString(0);
					String past = cursor.getString(1);
					String pronun = cursor.getString(2);
					String mean = cursor.getString(3);
					
					insert_Words(name, past, pronun, mean, 2);
					
					i++;
					
				}


			}
			
		}
		
		

}
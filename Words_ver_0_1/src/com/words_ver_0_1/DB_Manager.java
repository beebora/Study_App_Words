package com.words_ver_0_1;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DB_Manager {
	
		SQLiteDatabase db;
		int size_Words = 20;	//한번에 외울 단어 수
		int i;	//Words 수 측정에 쓸 임시 int
		
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
			/*All_Words로부터 단어를 추출해 Words로 채워넣음*/
			Cursor cursor = db.rawQuery("SELECT name, past, pronun, mean, taken FROM All_Words", null);
			i = 0;
			
			//하나씩 스트링에 넣고 출력
			while ( cursor.moveToNext() && (i < size_Words) ) {
				
				/*boolean이 true인 레코드에 대해서만 가져옴*/
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
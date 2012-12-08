package com.words_ver_0_1;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.io.*;

public class Testfordev extends Fragment {
	/**
	 * The fragment argument representing the section number for this fragment.
	 */

	EditText eWord, ePronun, eMeaning;
	TextView mShow;
	Button bSubmit;
	WordDBHelper mHelper;
	SQLiteDatabase db;
	Cursor cursor;
	DB_Manager db_Manager;

	public Testfordev() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Create a new TextView and set its text to the fragment's section
		// number argument value.
		View v = inflater.inflate(R.layout.testfordev, container, false);
		mHelper = new WordDBHelper(getActivity());
		db = mHelper.getWritableDatabase();
		//DB매니저 클래스
		db_Manager = new DB_Manager(db);

		eWord = (EditText) v.findViewById(R.id.editText1);
		ePronun = (EditText) v.findViewById(R.id.editText2);
		eMeaning = (EditText) v.findViewById(R.id.editText3);
		bSubmit = (Button) v.findViewById(R.id.button1);
		mShow = (TextView) v.findViewById(R.id.textView4);

		bSubmit.setOnClickListener(mClickListener);
		resultRefresh();
		return v;
	}

	/**
	 * temp탭에 보면 글 적는곳이 3칸 있는데, 단어이름, 발음, 뜻 이렇게 다 적으면 단어가 등록되고 나머지 두칸은 그대로 두고
	 * 이름칸만 적으면 그 이름칸의 name과 일치하는 인스턴스를 제거하는 버
	 */
	Button.OnClickListener mClickListener = new Button.OnClickListener() {
		public void onClick(View v) {

			if (ePronun.getText().toString().contentEquals("")
					|| eMeaning.getText().toString().contentEquals("")) {
				db.execSQL("DELETE from Words where name = '" + eWord.getText()
						+ "'");
			} else {
				db.execSQL("INSERT INTO Words VALUES (null, '"
						+ eWord.getText() + "', 'past', '" + ePronun.getText()
						+ "', '" + eMeaning.getText() + "', 3);");
			}
			eWord.setText(null);
			ePronun.setText(null);
			eMeaning.setText(null);
			

			resultRefresh();

			dic_File_Read(getActivity());
			db_Manager.fill_Words();
		}
	};

	/**
	 * 결과 새로고침
	 */
	public void resultRefresh() {
		db = mHelper.getReadableDatabase();
		cursor = db.rawQuery(
				"SELECT name, past, pronun, mean FROM All_Words", null);

		// 하나씩 스트링에 넣고 출력
		String Result = "";
		while (cursor.moveToNext()) {
			String name = cursor.getString(0);
			String past = cursor.getString(1);
			String pronun = cursor.getString(2);
			String mean = cursor.getString(3);
			//int level = cursor.getInt(4);
			Result += (name + ", " + past + ", " + pronun + ", " + mean + ", "
					/*+ level*/ + "\n");
		}
		if (Result.length() == 0) {
			mShow.setText("데이터가 없습니다");
		} else {
			mShow.setText(Result);
		}
		cursor.close();
	}

	/* dic.txt 쓰기(일단 시험용) */
	public void dic_File_Write(Context context) {

		String mSdPath;
		String ext = Environment.getExternalStorageState();

		/* SD카드 경로 알아냄 */
		if (ext.equals(Environment.MEDIA_MOUNTED)) {
			mSdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
		} else {
			mSdPath = Environment.MEDIA_UNMOUNTED;
		}

		File dir = new File(mSdPath + "/Mydir");
		dir.mkdir();

		File file = new File(mSdPath + "/Mydir/dic.txt");

		try {
			FileOutputStream fos = new FileOutputStream(file);
			String str = "Android Test423";
			fos.write(str.getBytes());
			fos.close();
		} catch (Exception e) {;}		

	}
	
	public void dic_File_Read(Context context) {

		String mSdPath;
		String ext = Environment.getExternalStorageState();
		
		/* SD카드 경로 알아냄 */
		if (ext.equals(Environment.MEDIA_MOUNTED)) {
			mSdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
		} else {
			mSdPath = Environment.MEDIA_UNMOUNTED;
		}

		File dir = new File(mSdPath + "/WordDic");
		dir.mkdir();

		try {
			
			FileInputStream fis = new FileInputStream(mSdPath + "/WordDic/dic.txt");
			byte[] data = new byte[fis.available()];
			while(fis.read(data) != -1) {;}
			fis.close();

			/*읽은 파일 쪼개서 데이터베이스에 Insert*/
			String dic[] = (new String(data)).split("\n");

			//db.execSQL("INSERT INTO Words VALUES (null, 'Apple', 'null', 'aple', '사과', 2);");
			for(int i=0; i<dic.length; i++){
				
				String temp[] = dic[i].split(",");
				
				/*All_Words 테이블에 넣음 ...*/
				db_Manager.insert_All_Words(temp[0], temp[1], temp[2], temp[3]);

			}
			
			
		} catch (FileNotFoundException e) {
			mShow.setText("File Not Found");
		}
		catch (Exception e) {;}		

	}

}

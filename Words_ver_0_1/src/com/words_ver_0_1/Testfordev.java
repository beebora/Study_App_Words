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
		//DB�Ŵ��� Ŭ����
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
	 * temp�ǿ� ���� �� ���°��� 3ĭ �ִµ�, �ܾ��̸�, ����, �� �̷��� �� ������ �ܾ ��ϵǰ� ������ ��ĭ�� �״�� �ΰ�
	 * �̸�ĭ�� ������ �� �̸�ĭ�� name�� ��ġ�ϴ� �ν��Ͻ��� �����ϴ� ��
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
	 * ��� ���ΰ�ħ
	 */
	public void resultRefresh() {
		db = mHelper.getReadableDatabase();
		cursor = db.rawQuery(
				"SELECT name, past, pronun, mean FROM All_Words", null);

		// �ϳ��� ��Ʈ���� �ְ� ���
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
			mShow.setText("�����Ͱ� �����ϴ�");
		} else {
			mShow.setText(Result);
		}
		cursor.close();
	}

	/* dic.txt ����(�ϴ� �����) */
	public void dic_File_Write(Context context) {

		String mSdPath;
		String ext = Environment.getExternalStorageState();

		/* SDī�� ��� �˾Ƴ� */
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
		
		/* SDī�� ��� �˾Ƴ� */
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

			/*���� ���� �ɰ��� �����ͺ��̽��� Insert*/
			String dic[] = (new String(data)).split("\n");

			//db.execSQL("INSERT INTO Words VALUES (null, 'Apple', 'null', 'aple', '���', 2);");
			for(int i=0; i<dic.length; i++){
				
				String temp[] = dic[i].split(",");
				
				/*All_Words ���̺� ���� ...*/
				db_Manager.insert_All_Words(temp[0], temp[1], temp[2], temp[3]);

			}
			
			
		} catch (FileNotFoundException e) {
			mShow.setText("File Not Found");
		}
		catch (Exception e) {;}		

	}

}

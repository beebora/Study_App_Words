package com.words_ver_0_1;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class LearningPart extends Fragment {

	public static final String ARG_SECTION_NUMBER = "section_number";

	int currentPosition;
	SQLiteDatabase db;
	WordDBHelper mHelper;
	public LearningPart(int position,SQLiteDatabase d, WordDBHelper mhelp ) {
		db = d;
		mHelper = mhelp;
		currentPosition = position;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Create a new TextView and set its text to the fragment's section
		// number argument value.
		View retnView = inflater.inflate(R.layout.learningpart, container, false);
		db = mHelper.getReadableDatabase();
		Cursor c = db.rawQuery("select name, pronun, mean from Words", null);
		c.moveToPosition(currentPosition);
		TextView wordName = (TextView) retnView.findViewById(R.id.learningpart_wordname);
		wordName.setText(c.getString(0).toUpperCase());
		wordName.setTextSize(35);
		TextView wordPron = (TextView) retnView.findViewById(R.id.learningpart_wordpronun);
		wordPron.setText(c.getString(1));
		wordPron.setTextSize(10);
		TextView wordMeaning = (TextView) retnView.findViewById(R.id.learningpart_wordmeaning);
		wordMeaning.setText(c.getString(2));
		return retnView;
	}
	/**
	 * �ɽ��ؼ� ����� �� �ļ�. ���� ���� ��, �޸��� �����ؼ� �������� ���� ��� �ڵ����� �����ؼ� ��ȣ�ٿ��ִ� �ڵ�. 
	 * new TempDataSet("respect","respect","�����ϴ�, ����, ����"); �̷��� �ǹ̸� �޸��� �и��ؼ� �־
	 * 1. �����ϴ�.
	 * 2. ����.
	 * 3. ����. 
	 * �̷��� �������� �ٲ��ش�.
	 */
	public String stringParser(String input){
		String retn =  "1. " + input ;
		int i = 1;
		retn = retn.replaceAll(", ", ",");
		retn = retn.replaceAll(" ,", ",");
		for(;retn.contains(",");)
			retn = retn.replaceFirst(",", ".\n"+ (++i) +". ");

		if(retn.charAt(retn.length()-1) != '.')
			retn = retn.concat(".");
		return retn;
	}
}

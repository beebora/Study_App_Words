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
	 * 심심해서 만들어 본 파서. 뜻을 적을 때, 콤마로 구분해서 여러뜻을 적은 경우 자동으로 개행해서 번호붙여주는 코드. 
	 * new TempDataSet("respect","respect","존경하다, 존경, 측면"); 이렇게 의미를 콤마로 분리해서 넣어도
	 * 1. 존경하다.
	 * 2. 존경.
	 * 3. 측면. 
	 * 이렇게 나오도록 바꿔준다.
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

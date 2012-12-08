package com.words_ver_0_1;

import java.util.ArrayList;

import android.database.sqlite.SQLiteDatabase;

public class TempDataSet {
	/**
	 * 단어 명
	 * 발음기호 
	 * 단어 뜻 
	 * 단어 레벨 (추출 알고리즘을 위해)
	 */
	public String Word_name;
	public String Pronun_Symbol;
	public String Word_Meaning;
	public int Word_Level;
	static SQLiteDatabase db;
	static WordDBHelper mHelper;
	public static ArrayList<TempDataSet> dataArray = new ArrayList<TempDataSet>();
	// 생성자(이 클래스가 처음 인스턴스로 생성될 때 실행되는 코드)
	// 입력값이 없는 생성, TempDataSet example = new TempDataSet();

	// 입력값이 있는 생성자. 이름과 발음기호, 뜻을 차례로 적어 실행시키면 된다.
	// 예를들어 TempDataSet example = new TempDataSet(Respect,[발음기호],존경하다) 
	TempDataSet(String name, String symbol, String meaning){
		Word_name = name;
		Word_Meaning = meaning;
		Pronun_Symbol = symbol;
		Word_Level = 0;
		dataArray.add(this);
	}
}

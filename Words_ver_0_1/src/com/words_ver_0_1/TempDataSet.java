package com.words_ver_0_1;

import java.util.ArrayList;

import android.database.sqlite.SQLiteDatabase;

public class TempDataSet {
	/**
	 * �ܾ� ��
	 * ������ȣ 
	 * �ܾ� �� 
	 * �ܾ� ���� (���� �˰����� ����)
	 */
	public String Word_name;
	public String Pronun_Symbol;
	public String Word_Meaning;
	public int Word_Level;
	static SQLiteDatabase db;
	static WordDBHelper mHelper;
	public static ArrayList<TempDataSet> dataArray = new ArrayList<TempDataSet>();
	// ������(�� Ŭ������ ó�� �ν��Ͻ��� ������ �� ����Ǵ� �ڵ�)
	// �Է°��� ���� ����, TempDataSet example = new TempDataSet();

	// �Է°��� �ִ� ������. �̸��� ������ȣ, ���� ���ʷ� ���� �����Ű�� �ȴ�.
	// ������� TempDataSet example = new TempDataSet(Respect,[������ȣ],�����ϴ�) 
	TempDataSet(String name, String symbol, String meaning){
		Word_name = name;
		Word_Meaning = meaning;
		Pronun_Symbol = symbol;
		Word_Level = 0;
		dataArray.add(this);
	}
}

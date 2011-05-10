package kuro075.poke.pokedatabase.data_base.history;

import kuro075.poke.pokedatabase.data_base.BasicData;



/**
 * ページの履歴データクラス
 * @author sanogenma
 *
 */
public class PageHistoryData extends BasicData{
	private final String date;//日時
	public PageHistoryData(int no,String name,String date){
		super(name,no);
		this.date=date;
	}
	public String getDate(){
		return date;
	}
}

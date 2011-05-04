package kuro075.poke.pokedatabase.poke_book.search_result;

/**
 * 検索結果のリストに表示する項目
 * @author sanogenma
 *
 */
public class ListItemBean {
	private String No;
	private String Name;
	private String Info;
	
	
	public void setNo(String no) {
		No = no;
	}
	public String getNo() {
		return No;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getName() {
		return Name;
	}
	public void setInfo(String info) {
		Info = info;
	}
	public String getInfo() {
		return Info;
	}
}

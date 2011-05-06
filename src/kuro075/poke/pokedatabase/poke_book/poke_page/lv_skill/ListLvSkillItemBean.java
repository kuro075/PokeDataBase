package kuro075.poke.pokedatabase.poke_book.poke_page.lv_skill;

/**
 * レベルアップで覚える技のリスト項目
 * @author sanogenma
 *
 */
public class ListLvSkillItemBean {
	private String DP;
	private String Pt;
	private String HS;
	private String BW;
	private String SkillName;
	private String Note;
	public void setDP(String dP) {
		DP = dP;
	}
	public String getDP() {
		return DP;
	}
	public void setPt(String pt) {
		Pt = pt;
	}
	public String getPt() {
		return Pt;
	}
	public void setHS(String hS) {
		HS = hS;
	}
	public String getHS() {
		return HS;
	}
	public void setBW(String bW) {
		BW = bW;
	}
	public String getBW() {
		return BW;
	}
	public void setSkillName(String skillName) {
		SkillName = skillName;
	}
	public String getSkillName() {
		return SkillName;
	}
	public void setNote(String note) {
		Note = note;
	}
	public String getNote() {
		return Note;
	}
}

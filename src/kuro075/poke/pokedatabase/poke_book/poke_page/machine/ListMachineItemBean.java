package kuro075.poke.pokedatabase.poke_book.poke_page.machine;

/**
 * わざマシンリスト項目
 * @author sanogenma
 *
 */
public class ListMachineItemBean {
	private String machineNo;
	private String SkillName;
	public void setMachineNo(String machineNo) {
		this.machineNo = machineNo;
	}
	public String getMachineNo() {
		return machineNo;
	}
	public void setSkillName(String skillName) {
		SkillName = skillName;
	}
	public String getSkillName() {
		return SkillName;
	}

}

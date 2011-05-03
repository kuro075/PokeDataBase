package kuro075.poke.pokedatabase.data_base.skill;

/**
 * 検索されたわざ配列を受け取るリスナー
 * @author sanogenma
 *
 */
public interface SearchedSkillListener {
	/**
	 * 検索後のわざ配列と検索条件を受け取るメソッド
	 * @param skill_array　検索後のわざ配列
	 * @param search_condition　検索条件
	 */
	public void receiveSkillArray(SkillData[] skill_array,String search_condition);
}

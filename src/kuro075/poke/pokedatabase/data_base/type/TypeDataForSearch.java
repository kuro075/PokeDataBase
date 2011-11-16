package kuro075.poke.pokedatabase.data_base.type;

import java.util.ArrayList;
import java.util.List;

import kuro075.poke.pokedatabase.data_base.BasicData;
import kuro075.poke.pokedatabase.data_base.poke.PokeData;
import kuro075.poke.pokedatabase.data_base.poke.PokeDataManager;
import kuro075.poke.pokedatabase.data_base.skill.SkillData;
import kuro075.poke.pokedatabase.data_base.skill.SkillDataManager;

public class TypeDataForSearch extends BasicData{

	private final TypeDataManager.TypeData type_data1,type_data2;
	//このタイプのポケモンリスト
	private final List<PokeData> poke_list=new ArrayList<PokeData>();
	private boolean flag_poke_list=false;
	//このタイプのわざリスト
	private final List<SkillData> skill_list=new ArrayList<SkillData>();
	private boolean flag_skill_list=false;
	//このタイプの弱点・抵抗の数
	private final int num_weak_type,num_resist_type;
	
	private static final int NUM_SPEC=7;//種族値の種類の数
	//最高・最低・平均種族値とそのポケモンとその数と更新フラグ
	private final int[] max_spec=new int[NUM_SPEC],min_spec=new int[NUM_SPEC],ave_spec=new int[NUM_SPEC];
	private final PokeData[] poke_max_spec=new PokeData[NUM_SPEC],poke_min_spec=new PokeData[NUM_SPEC];
	private final int[] num_poke_max_spec=new int[NUM_SPEC],num_poke_min_spec=new int[NUM_SPEC];
	private final boolean[] flag_max_spec=new boolean[NUM_SPEC],flag_min_spec=new boolean[NUM_SPEC],flag_ave_spec=new boolean[NUM_SPEC];
	
	
	protected TypeDataForSearch(String name,int no,TypeDataManager.TypeData type){
		super(name,no);
		type_data1=type;
		type_data2=null;
		initFlag();
		num_weak_type=TypeDataManager.getWeakTypes(type_data1, type_data2, TypeDataManager.TypeRelations._400).length
		             +TypeDataManager.getWeakTypes(type_data1, type_data2, TypeDataManager.TypeRelations._200).length;
		num_resist_type=TypeDataManager.getWeakTypes(type_data1, type_data2, TypeDataManager.TypeRelations._50).length
					   +TypeDataManager.getWeakTypes(type_data1, type_data2, TypeDataManager.TypeRelations._25).length
					   +TypeDataManager.getWeakTypes(type_data1, type_data2, TypeDataManager.TypeRelations._0).length;
	}
	
	protected TypeDataForSearch(String name, int no,TypeDataManager.TypeData type1,TypeDataManager.TypeData type2) {
		super(name, no);
		type_data1=type1;
		type_data2=type2;
		initFlag();
		num_weak_type=TypeDataManager.getWeakTypes(type_data1, type_data2, TypeDataManager.TypeRelations._400).length
			         +TypeDataManager.getWeakTypes(type_data1, type_data2, TypeDataManager.TypeRelations._200).length;
		num_resist_type=TypeDataManager.getWeakTypes(type_data1, type_data2, TypeDataManager.TypeRelations._50).length
		   			   +TypeDataManager.getWeakTypes(type_data1, type_data2, TypeDataManager.TypeRelations._25).length
		   			   +TypeDataManager.getWeakTypes(type_data1, type_data2, TypeDataManager.TypeRelations._0).length;
	}
	
	/**
	 * 各更新フラグを初期化
	 */
	private void initFlag(){
		for(int i=0;i<NUM_SPEC;i++){
			flag_max_spec[i]=false;
			flag_min_spec[i]=false;
			flag_ave_spec[i]=false;
		}
	}
	
	/**
	 * このタイプのポケモンリストを初期化
	 */
	public void initPokeList(){
		if(!flag_poke_list){
			for(PokeData poke:PokeDataManager.INSTANCE.getAllData()){
				if(poke.hasType(type_data1)){
					if(type_data2==null || poke.hasType(type_data2)){
						poke_list.add(poke);
					}
				}
			}
			flag_poke_list=true;
		}
	}
	
	/**
	 * このタイプのわざリストを初期化
	 */
	public void initSkillList(){
		if(!flag_skill_list){
			for(SkillData skill:SkillDataManager.INSTANCE.getAllData()){
				if(skill.getType().equals(type_data1) || (isMultipleType() && skill.getType().equals(type_data2))){
					skill_list.add(skill);
				}
			}
			flag_skill_list=true;
		}
	}
	
	
	/**
	 * このタイプでの最大種族値を取得
	 * @param status
	 * @return
	 */
	public int getMaxSpec(PokeData.Statuses status){
		if(!flag_max_spec[status.ordinal()]){
			initPokeList();
			int max=0;
			int num=0;
			PokeData max_poke=PokeDataManager.NullData;
			for(PokeData poke:poke_list){
				int spec=poke.getSpec(status);
				if(spec>max){
					max=spec;
					max_poke=poke;
					num=1;
				}else if(spec==max){
					num++;
				}
			}
			max_spec[status.ordinal()]=max;
			poke_max_spec[status.ordinal()]=max_poke;
			num_poke_max_spec[status.ordinal()]=num;
			flag_max_spec[status.ordinal()]=true;
		}
		return max_spec[status.ordinal()];
	}
	
	/**
	 * このタイプでの最小種族値を取得
	 * @param status
	 * @return
	 */
	public int getMinSpec(PokeData.Statuses status){
		if(!flag_min_spec[status.ordinal()]){
			initPokeList();
			int min=800;
			int num=0;
			PokeData min_poke=PokeDataManager.NullData;
			for(PokeData poke:poke_list){
				int spec=poke.getSpec(status);
				if(spec<min){
					min=spec;
					min_poke=poke;
					num=1;
				}else if(spec==min){
					num++;
				}
			}
			if(min==800){
				min=0;
			}
			
			min_spec[status.ordinal()]=min;
			poke_min_spec[status.ordinal()]=min_poke;
			num_poke_min_spec[status.ordinal()]=num;
			flag_min_spec[status.ordinal()]=true;
		}
		return min_spec[status.ordinal()];
	}
	
	/**
	 * このタイプでの平均種族値を取得
	 * @return
	 */
	public int getAveSpec(PokeData.Statuses status){
		if(!flag_ave_spec[status.ordinal()]){
			initPokeList();
			int sum=0;
			for(PokeData poke:poke_list){
				sum+=poke.getSpec(status);
			}
			if(poke_list.size()>0){
				sum/=poke_list.size();
			}else{
				sum=0;
			}
			ave_spec[status.ordinal()]=sum;
			flag_ave_spec[status.ordinal()]=true;
		}
		return ave_spec[status.ordinal()];
	}
	
	/**
	 * このタイプのポケモンの数
	 * @return
	 */
	public int getNumPoke(){
		initPokeList();
		return poke_list.size();
	}
	
	/**
	 * このタイプのポケモンのリストを取得
	 * @return
	 */
	public PokeData[] getPokeArray(){
		initPokeList();
		return poke_list.toArray(new PokeData[0]);
	}
	/**
	 * このタイプのわざの数
	 * @return
	 */
	public int getNumSkill(){
		initSkillList();
		return skill_list.size();
	}
	/**
	 * このタイプのわざのリストを取得
	 * @return
	 */
	public SkillData[] getSkillArray(){
		initSkillList();
		return skill_list.toArray(new SkillData[0]);
	}
	
	
	/**
	 * 単タイプかどうかを取得
	 * @return
	 */
	public boolean isSingleType(){
		return type_data2==null;
	}

	/**
	 * 複合タイプかどうかを取得
	 * @return
	 */
	public boolean isMultipleType(){
		return type_data2!=null;
	}
	
	/**
	 * タイプ１を取得
	 * @return
	 */
	public TypeDataManager.TypeData getType1(){
		return type_data1;
	}
	
	/**
	 * タイプ２を取得
	 * @return
	 */
	public TypeDataManager.TypeData getType2(){
		return type_data2;
	}
	
	/**
	 * このタイプかどうか
	 * @param type1
	 * @param type2
	 * @return
	 */
	public boolean isType(TypeDataManager.TypeData type1,TypeDataManager.TypeData type2){
		if(type_data2==null){
			return type_data1.equals(type1) && type2==null;
		}
		return type_data1.equals(type1) && type_data2.equals(type2);
	}
	
	/**
	 * 弱点の数を取得
	 * @return
	 */
	public int getNumWeakType(){
		return num_weak_type;
	}
	
	/**
	 * 抵抗の数を取得
	 * @return
	 */
	public int getNumResistType(){
		return num_resist_type;
	}
}
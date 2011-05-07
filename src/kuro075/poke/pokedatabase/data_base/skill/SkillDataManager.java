package kuro075.poke.pokedatabase.data_base.skill;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import kuro075.poke.pokedatabase.data_base.SearchTypes;
import kuro075.poke.pokedatabase.data_base.skill.SkillData.AttackTargets;
import kuro075.poke.pokedatabase.data_base.type.TypeDataManager;
import kuro075.poke.pokedatabase.util.Utility;
import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;

/**
 * 技データ管理クラス
 * @author sanogenma
 *
 */
public class SkillDataManager {

	private static final String TAG="SkillDataManager";
	public static final SkillDataManager INSTANCE = new Builder().build();
	
	//================================================================================
	/*==========/
	/  Builder  / 
	/==========*/
	private static class Builder{
		List<SkillData> skill_list=new ArrayList<SkillData>();
		
		private Builder(){
			readFile();
		}
		private void readFile(){
			Utility.log(TAG,"readSkillData");
			FileInputStream fis = null;
			BufferedReader br=null;
			String line,tmp;//
			StringTokenizer st;
			int no=0;
			
			try{
				fis = new FileInputStream(kuro075.poke.pokedatabase.util.Utility.DATAPATH+kuro075.poke.pokedatabase.util.Utility.FileNames.SKILL);
				br=new BufferedReader(new InputStreamReader(fis));
				
				while((line=br.readLine())!=null){
					st = new StringTokenizer(line);//トークンに分ける
					if(st.hasMoreTokens()){
						tmp=st.nextToken();
						if(st.hasMoreTokens()){
							SkillData.Builder skill_builder=new SkillData.Builder();
							//==============================================================
							//わざの管理ナンバー
							//Utility.log(TAG,"No."+no);
							skill_builder.setNo(no);
							no++;
							//==============================================================
							//わざの名前
							skill_builder.setName(tmp);
							//==============================================================
							//わざのタイプ
							skill_builder.setType(TypeDataManager.TypeData.fromInteger(Integer.parseInt(st.nextToken())));
							//==============================================================
							//わざの分類
							skill_builder.setSkillClass(SkillData.SkillClasses.fromInteger(Integer.parseInt(st.nextToken())));
							//==============================================================
							//わざの威力
							skill_builder.setPower(Integer.parseInt(st.nextToken()));
							//==============================================================
							//わざの命中
							skill_builder.setHit(Integer.parseInt(st.nextToken()));
							//==============================================================
							//わざのPP
							skill_builder.setPp(Integer.parseInt(st.nextToken()));
							//==============================================================
							//わざの直接攻撃かどうか
							skill_builder.setDirect(SkillData.WhetherDirect.getWhetherDirectFromBoolean(st.nextToken().equals("1")));
							//==============================================================
							//わざの対象
							skill_builder.setTarget(AttackTargets.fromInteger(Integer.parseInt(st.nextToken())));
							//==============================================================
							//わざの効果
							skill_builder.setEffect(st.nextToken());
							//==============================================================
							//わざの優先度
							if(st.hasMoreTokens()){
								skill_builder.setPriority(Integer.parseInt(st.nextToken()));
							}else{
								skill_builder.setPriority(0);
							}
							//==============================================================
							//SkillDataをビルドして登録
							skill_list.add(skill_builder.build());
						}
					}
					
				}
				
			}catch(IOException e){
				Log.e(TAG,"skilldata read error");
			}finally{
				try{
					if(br!=null){
						br.close();
					}
				}catch(IOException e){
					
				}
			}
			Utility.log(TAG,"end readFile");
		}
		
		
		private SkillDataManager build(){
			return new SkillDataManager(skill_list.toArray(new SkillData[0]));
		}
	}
	//================================================================================
	/*=======/
	/  enum  / 
	/=======*/
	public enum ViewableInformations{
		TYPE("タイプ") {
			@Override
			public String getInformation(SkillData skill) {
				// TODO Auto-generated method stub
				return skill.getType().toString();
			}

			@Override
			public Comparator<SkillData> getComparator() {
				// TODO Auto-generated method stub
				return new Comparator<SkillData>(){
					@Override
					public int compare(SkillData s1, SkillData s2) {
						// TODO Auto-generated method stub
						final int d=s1.getType().getIndex()-s2.getType().getIndex();
						if(d==0){
							return s1.getNo()-s2.getNo();
						}
						return d;
					}
				};
			}
		},
		SKILL_CLASS("分類"){
			@Override
			public String getInformation(SkillData skill) {
				// TODO Auto-generated method stub
				return skill.getSkillClass().toString();
			}

			@Override
			public Comparator<SkillData> getComparator() {
				// TODO Auto-generated method stub
				return new Comparator<SkillData>(){
					@Override
					public int compare(SkillData s1, SkillData s2) {
						// TODO Auto-generated method stub
						final int d=s1.getSkillClass().getIndex()-s2.getSkillClass().getIndex();
						if(d==0){
							return s1.getNo()-s2.getNo();
						}
						return d;
					}
				};
			}
			
		},
		POWER("威力"){
			@Override
			public String getInformation(SkillData skill) {
				// TODO Auto-generated method stub
				return String.valueOf(skill.getPower());
			}

			@Override
			public Comparator<SkillData> getComparator() {
				// TODO Auto-generated method stub
				return new Comparator<SkillData>(){
					@Override
					public int compare(SkillData s1, SkillData s2) {
						// TODO Auto-generated method stub
						final int d=s1.getPower()-s2.getPower();
						if(d==0){
							return s1.getNo()-s2.getNo();
						}
						return d;
					}
				};
			}
		},
		HIT("命中率") {
			@Override
			public String getInformation(SkillData skill) {
				// TODO Auto-generated method stub
				return String.valueOf(skill.getHit());
			}

			@Override
			public Comparator<SkillData> getComparator() {
				// TODO Auto-generated method stub
				return new Comparator<SkillData>(){
					@Override
					public int compare(SkillData s1, SkillData s2) {
						// TODO Auto-generated method stub
						final int d=s1.getHit()-s2.getHit();
						if(d==0){
							return s1.getNo()-s2.getNo();
						}
						return d;
					}
				};
			}
		},
		PP("PP") {
			@Override
			public String getInformation(SkillData skill) {
				// TODO Auto-generated method stub
				return String.valueOf(skill.getPp());
			}

			@Override
			public Comparator<SkillData> getComparator() {
				// TODO Auto-generated method stub
				return new Comparator<SkillData>(){
					@Override
					public int compare(SkillData s1, SkillData s2) {
						// TODO Auto-generated method stub
						final int d=s1.getPp()-s2.getPp();
						if(d==0){
							return s1.getNo()-s2.getNo();
						}
						return d;
					}
				};
			}
		},
		DIRECT("直接攻撃") {
			@Override
			public String getInformation(SkillData skill) {
				// TODO Auto-generated method stub
				return skill.getDirect().isDirect()?"○":"×";
			}

			@Override
			public Comparator<SkillData> getComparator() {
				// TODO Auto-generated method stub
				return new Comparator<SkillData>(){
					@Override
					public int compare(SkillData s1, SkillData s2) {
						// TODO Auto-generated method stub
						final int d=s1.getDirect().getIndex()-s2.getDirect().getIndex();
						if(d==0){
							return s1.getNo()-s2.getNo();
						}
						return d;
					}
				};
			}
		},
		TARGET("攻撃対象") {
			@Override
			public String getInformation(SkillData skill) {
				// TODO Auto-generated method stub
				return skill.getTarget().toString();
			}

			@Override
			public Comparator<SkillData> getComparator() {
				// TODO Auto-generated method stub
				return new Comparator<SkillData>(){
					@Override
					public int compare(SkillData s1, SkillData s2) {
						// TODO Auto-generated method stub
						final int d=s1.getTarget().getIndex()-s2.getTarget().getIndex();
						if(d==0){
							return s1.getNo()-s2.getNo();
						}
						return d;
					}
				};
			}
		},
		EFFECT("効果") {
			@Override
			public String getInformation(SkillData skill) {
				// TODO Auto-generated method stub
				return skill.getOutlineEffect();
			}

			@Override
			public Comparator<SkillData> getComparator() {
				// TODO Auto-generated method stub
				return new Comparator<SkillData>(){
					@Override
					public int compare(SkillData s1, SkillData s2) {
						// TODO Auto-generated method stub
						return s1.getNo()-s2.getNo();
					}
				};
			}
		},
		PRIORITY("優先度") {
			@Override
			public String getInformation(SkillData skill) {
				// TODO Auto-generated method stub
				StringBuilder sb=new StringBuilder();
				final int priority=skill.getPriority();
				if(priority>0){
					sb.append("+");
				}
				sb.append(priority);
				return new String(sb);
			}

			@Override
			public Comparator<SkillData> getComparator() {
				// TODO Auto-generated method stub
				return new Comparator<SkillData>(){
					@Override
					public int compare(SkillData s1, SkillData s2) {
						// TODO Auto-generated method stub
						final int d=s1.getPriority()-s2.getPriority();
						if(d==0){
							return s1.getNo()-s2.getNo();
						}
						return d;
					}
				};
			}
		};
		
		
		/*========/
		/  メンバ  /
		/========*/
		private final String name;
		ViewableInformations(String name){this.name=name;}
		@Override
		public String toString(){return name;}
		/**
		 * 表示する情報を取得
		 * @param skill
		 * @return
		 */
		abstract public String getInformation(SkillData skill);
		/**
		 * ソートに使うComparatorを取得
		 * @return
		 */
		abstract public Comparator<SkillData> getComparator();
	}
	
	/**
	 * 検索可能な情報
	 * @author sanogenma
	 *
	 */
	public enum SearchableInformations{
		NAME("名前") {
			@Override
			public AlertDialog getDialog(Context context, SkillData[] skills,
					SearchTypes search_type, SearchedSkillListener listener) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public SkillData[] search(SkillData[] skill_array, String condition) {
				// TODO Auto-generated method stub
				return null;
			}
		};
		private final String name;
		SearchableInformations(String name){this.name=name;}
		@Override
		public String toString(){return name;}

		/**
		 * ダイアログを取得するメソッド
		 * @param context
		 * @param skills
		 * @param search_type
		 * @param listener
		 * @return
		 */
		abstract public AlertDialog getDialog(Context context,SkillData[] skills,SearchTypes search_type,SearchedSkillListener listener);
		/**
		 * 検索条件(文字列)からポケモンを検索して返すメソッド
		 * @param condition　検索条件
		 * @return
		 */
		abstract public SkillData[] search(SkillData[] skill_array,String condition);
		/**
		 * condition=種類:hoge(SearchTypes)
		 * @param skill_array
		 * @param condtion
		 * @return
		 */
		public static SkillData[] searchByCondition(SkillData[] skill_array,String condition){
			String[] tmp=condition.split(":()");
			return fromString(tmp[0]).search(skill_array, tmp[1]);
		}
		
		private static final Map<String,SearchableInformations>
			stringToEnum = new HashMap<String,SearchableInformations>();//文字列からenumへ
		static { //定数名からenum定数へのマップを初期化
			for(SearchableInformations si : values()){
				stringToEnum.put(si.toString(), si);
			}
		}
		/**
		 * 文字列からSearchableInformationsを取得
		 * @param step
		 * @return
		 */
		public static SearchableInformations fromString(String name){
			return stringToEnum.get(name);
		}
		
	}
	
	//================================================================================
	/*========/
	/  データ  / 
	/========*/
	private final SkillData[] skill_data;
	private final String[] skill_name;
	private final Map<String,SkillData> name2skill_map=new HashMap<String,SkillData>();
	private final int num;
	public static final SkillData NullData=new SkillData.Builder().build();
	private SkillDataManager(SkillData[] skill_data){
		this.skill_data=skill_data;
		num=this.skill_data.length;
		for(SkillData skill:skill_data){
			name2skill_map.put(skill.toString(), skill);
		}
		skill_name=Utility.changeToStringArray(skill_data);
	}
	//================================================================================
	/*==========/
	/  ゲッター  /
	/==========*/
	/**
	 * skill_dataの数を返す
	 */
	public int getNum(){
		return num;
	}
	
	/**
	 * 全ての技データを取得
	 * @return
	 */
	public SkillData[] getAllSkillData(){
		return skill_data;
	}
	/**
	 * 技名配列を取得
	 * @return
	 */
	public String[] getAllSkillName(){
		return skill_name;
	}
	/**
	 * SkillDataをインデックスから取得
	 * @param index　インデックス
	 * @return　SkillData
	 */
	public SkillData getSkillData(int index){
		if(index<0 || num<=index) return NullData;
		return skill_data[index];
	}
	
	/**
	 * SkillDataを名前から取得
	 * @param name　名前
	 * @return　SkillData
	 */
	public SkillData getSkillData(String name){
		return name2skill_map.get(name);
	}
}

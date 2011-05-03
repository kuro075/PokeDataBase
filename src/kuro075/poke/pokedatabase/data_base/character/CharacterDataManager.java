package kuro075.poke.pokedatabase.data_base.character;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import kuro075.poke.pokedatabase.data_base.SearchTypes;
import kuro075.poke.pokedatabase.data_base.skill.SkillDataManager.SearchableInformations;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;

/**
 * 特性データマネージャー
 * @author sanogenma
 *
 */
public class CharacterDataManager {
	private static final String TAG="CharacterDataManager";
	public static final CharacterDataManager INSTANCE=new Builder().build();
	
	//================================================================================
	/*==========/
	/  Builder  / 
	/==========*/
	private static class Builder{
		List<CharacterData> chara_list=new ArrayList<CharacterData>();
		
		private Builder(){
			readFile();
		}
		private void readFile(){
			Log.v(TAG,"readCharacterData");
			FileInputStream fis = null;
			BufferedReader br=null;
			String line,tmp;//
			StringTokenizer st;
			int num=0;
			
			try{
				fis = new FileInputStream(kuro075.poke.pokedatabase.util.Utility.DATAPATH+kuro075.poke.pokedatabase.util.Utility.FileNames.CHARACTER);
				br = new BufferedReader(new InputStreamReader(fis));
				
				while((line=br.readLine())!=null){
					st = new StringTokenizer(line);//トークンに分ける
					if(st.hasMoreTokens()){
						tmp=st.nextToken();
						if(st.hasMoreTokens()){
							CharacterData.Builder chara_builder=new CharacterData.Builder();
							//================================================================
							Log.v(TAG,"No."+num);
							chara_builder.setNo(num);
							chara_builder.setName(tmp);
							chara_builder.setBattle_effect(st.nextToken());
							chara_builder.setField_effect(st.nextToken());
							chara_list.add(chara_builder.build());
							num++;
						}
					}
				}
			}catch(IOException e){
				e.printStackTrace();
			}finally{
				try{
					if(br!=null){
						br.close();
					}
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}
		private CharacterDataManager build(){
			return new CharacterDataManager(chara_list.toArray(new CharacterData[0]));
		}
	}
	
	//================================================================================
	/*=======/
	/  enum  / 
	/=======*/
	public enum ViewableInformations{
		BATTLE_EFFECT("戦闘中の効果") {
			@Override
			public String getInformation(CharacterData chara) {
				// TODO Auto-generated method stub
				return chara.getBattleEffect();
			}

			@Override
			public Comparator<CharacterData> getComparator() {
				// TODO Auto-generated method stub
				return new Comparator<CharacterData>(){
					@Override
					public int compare(CharacterData c1, CharacterData c2) {
						// TODO Auto-generated method stub
						return c1.getBattleEffect().compareTo(c2.getBattleEffect());
					}
				};
			}
		},
		FIELD_EFFECT("フィールド上の効果") {
			@Override
			public String getInformation(CharacterData chara) {
				// TODO Auto-generated method stub
				return chara.getFieldEffect();
			}

			@Override
			public Comparator<CharacterData> getComparator() {
				// TODO Auto-generated method stub
				return new Comparator<CharacterData>(){
					@Override
					public int compare(CharacterData c1, CharacterData c2) {
						// TODO Auto-generated method stub
						return c1.getFieldEffect().compareTo(c2.getFieldEffect());
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
		 * @param chara
		 * @return
		 */
		abstract public String getInformation(CharacterData chara);
		/**
		 * ソートに使うComparatorを取得
		 * @return
		 */
		abstract public Comparator<CharacterData> getComparator();
	}
	
	public enum SearchableInformations{
		NAME("名前") {
			@Override
			public AlertDialog getDialog(Context context,
					CharacterData[] charas, SearchTypes search_type,
					SearchedCharacterListener listener) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public CharacterData[] search(CharacterData[] chara_array,
					String condition) {
				// TODO Auto-generated method stub
				return null;
			}
		};
		
		private final String name;
		SearchableInformations(String name){this.name=name;}
		@Override
		public String toString(){
			return name;
		}
		/**
		 * ダイアログを取得するメソッド
		 * @param context
		 * @param charas
		 * @param search_type
		 * @param listener
		 * @return
		 */
		abstract public AlertDialog getDialog(Context context,CharacterData[] charas,SearchTypes search_type,SearchedCharacterListener listener);
		/**
		 * 検索条件(文字列)からわざを検索して返すメソッド
		 * @param chara_array
		 * @param condition
		 * @return
		 */
		abstract public CharacterData[] search(CharacterData[] chara_array,String condition);
		/**
		 * condition=種類:hoge(SearchTypes)
		 * @param chara_array
		 * @param condition
		 * @return
		 */
		public static CharacterData[] searchByCondition(CharacterData[] chara_array,String condition){
			String[] tmp=condition.split(":()");
			return fromString(tmp[0]).search(chara_array,tmp[1]);
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
	private final CharacterData[] chara_data;
	private final String[] chara_name;
	private final int num;
	public static final CharacterData NullData=new CharacterData.Builder().build();
	
	private CharacterDataManager(CharacterData[] chara_data){
		this.chara_data=chara_data;
		num=this.chara_data.length;
		chara_name=new String[num];
		for(int i=0;i<num;i++){
			this.chara_name[i]=chara_data[i].toString();
		}
	}
	//================================================================================
	/*==========/
	/  ゲッター  /
	/==========*/
	/**
	 * chara_dataの数を返す
	 */
	public int getNum(){
		return num;
	}
	
	/**
	 * CharacterDataをインデックスから取得
	 * @param index　インデックス
	 * @return CharacterData
	 */
	public CharacterData getCharacterData(int index){
		if(index<0 || num<=index)return NullData;
		return chara_data[index];
	}
	
	/**
	 * CharacterDataを名前から取得
	 * @param name　名前
	 * @return　CharacterData
	 */
	public CharacterData getCharacterData(String name){
		final int index=Arrays.binarySearch(chara_name, name);
		return getCharacterData(index);
	}
}

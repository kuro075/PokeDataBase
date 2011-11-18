package kuro075.poke.pokedatabase.data_base.character;

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
import kuro075.poke.pokedatabase.util.Utility;

import android.app.AlertDialog;
import android.content.Context;

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
			Utility.log(TAG,"readCharacterData");
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
							//Utility.log(TAG,"No."+num);
							chara_builder.setNo(num);
							chara_builder.setName(tmp);
							chara_builder.setBattle_effect(st.nextToken());
							chara_builder.setField_effect(st.nextToken());
							//発動タイミング
							String next=st.nextToken();
							String[] split=next.split(",");
							for(String timing:split){
								chara_builder.addTiming(CharacterData.Timings.fromName(timing));
							}
							//対象
							next=st.nextToken();
							split=next.split(",");
							for(String target:split){
								chara_builder.addTarget(CharacterData.Targets.fromName(target));
							}
							//効果の種類
							next=st.nextToken();
							split=next.split(",");
							for(String kind:split){
								chara_builder.addKind(CharacterData.Kinds.fromName(kind));
							}
							
							
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
	/*========/
	/  データ  / 
	/========*/
	private final CharacterData[] chara_data;
	private final String[] chara_name;
	private final Map<String,CharacterData> name2chara_map=new HashMap<String,CharacterData>();
	private final int num;
	public static final CharacterData NullData=new CharacterData.Builder().build();
	
	private CharacterDataManager(CharacterData[] chara_data){
		this.chara_data=chara_data;
		num=this.chara_data.length;
		for(CharacterData character:chara_data){
			name2chara_map.put(character.toString(), character);
		}
		chara_name=Utility.changeToStringArray(chara_data);
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
	 * 全てのCharacterDataを取得
	 * @return
	 */
	public CharacterData[] getAllData(){
		return chara_data.clone();
	}
	/**
	 * chara_nameを取得
	 * @return
	 */
	public String[] getAllCharaName(){
		return chara_name.clone();
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
		return name2chara_map.get(name);
	}
}

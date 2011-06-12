package kuro075.poke.pokedatabase.data_base.poke;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;

import kuro075.poke.pokedatabase.data_base.SearchIfListener;
import kuro075.poke.pokedatabase.data_base.SearchTypes;
import kuro075.poke.pokedatabase.data_base.item.ItemData;
import kuro075.poke.pokedatabase.data_base.poke.PokeData.CharacterTypes;
import kuro075.poke.pokedatabase.data_base.poke.PokeData.EggGroups;
import kuro075.poke.pokedatabase.data_base.poke.PokeData.ItemRarities;
import kuro075.poke.pokedatabase.data_base.poke.PokeData.Sexes;
import kuro075.poke.pokedatabase.data_base.type.TypeDataManager;
import kuro075.poke.pokedatabase.data_base.type.TypeDataManager.TypeData;
import kuro075.poke.pokedatabase.util.Utility;

/**
 * ポケモンデータ管理クラス
 * 
 * @author sanogenma
 *
 */
public class PokeDataManager implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String TAG="PokeDataManager";
	public static final PokeDataManager INSTANCE=new Builder().build();
	
	//=========================================================================
	/*============/
	/   Builder   /
	/============*/
	private static class Builder{
		private static final String filename="PokeDataManager.ser";
		//pokedataのラベル(1行のデータの書いてある順)
		private static final String[] DATA_LABEL={/*"No","名前","種族値","進化系列",*/"タイプ"/*,"とくせい","進化条件","努力値","持っている道具","タマゴグループ","孵化歩数","性別比率","捕獲しやすさ","初期なつき度","基礎経験値","最終経験値","HW","レベルアップで覚える技"*/,"タマゴわざ","わざマシン","旧わざマシン","教え技","HS教え技","BW教え技"};
		
		PokeDataManager manager=null;
		private Builder(){
			final String filepath=kuro075.poke.pokedatabase.util.Utility.DATAPATH+filename;
			/*if(new File(filepath).exists()){
				Log.v(TAG,"Deserialize");
				try{
					FileInputStream fis = new FileInputStream(filepath);
					ObjectInputStream ois = new ObjectInputStream(fis);
					manager = (PokeDataManager)ois.readObject();
					ois.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}*/
			if(manager==null){
				manager=readFile();
				/*
				//シリアライズ
				Log.v(TAG,"Serialize");
				try{
					FileOutputStream fos = new FileOutputStream(filepath);
					ObjectOutputStream os = new ObjectOutputStream(fos);
					
					os.writeObject(manager);
					os.close();
				}catch(Exception e){
					e.printStackTrace();
				}*/
			}
		}
		private PokeDataManager readFile(){
			Log.v(TAG,"readFile");
			List<PokeData> poke_list=new ArrayList<PokeData>();
			BufferedReader br=null;
			String line,tmp;//
			StringTokenizer st;//トークナイザー
			Pattern numpattern=Pattern.compile("[0-9]+");
			Matcher matcher;
			FileInputStream fis = null;
			try{
				fis=new FileInputStream(kuro075.poke.pokedatabase.util.Utility.DATAPATH+kuro075.poke.pokedatabase.util.Utility.FileNames.POKEMON);
				br = new BufferedReader(new InputStreamReader(fis));
				line=br.readLine();
				st = new StringTokenizer(line);//トークンに分ける
				st.nextToken();
				
				int labelindex=0;//DATA_LABELのインデックス　引用したらインクリメントする
				
				while((line=br.readLine())!=null){
					st = new StringTokenizer(line);//トークンに分ける
					if(st.hasMoreTokens()){
						tmp=st.nextToken();//最初のトークン
						matcher=numpattern.matcher(tmp);
						if(matcher.matches()){//最初のトークンが番号なら
							labelindex=0;
							//PokeData.Builderを作成
							PokeData.Builder poke_builder=new PokeData.Builder();
							//Log.v(TAG,"No."+tmp);
							poke_builder.setNo(Integer.valueOf(tmp));//図鑑No
							poke_builder.setName(st.nextToken());//名前
							//====================================================================================
							/*========/
							/  種族値  /
							/========*/
							for(int i=0;i<6;i++){
								poke_builder.setSpec(i,Integer.parseInt(st.nextToken()));
							}
							//====================================================================================
							/*=========/
							/  進化系列  /
							/=========*/
							int num_evolutions=0;
							for(tmp=st.nextToken();!tmp.equals(DATA_LABEL[labelindex]);tmp=st.nextToken(),num_evolutions++){//タイプ
								poke_builder.addEvolutions(Integer.parseInt(tmp));
							}
							labelindex++;//DATA_LABELインデックスインクリメント
							//====================================================================================
							/*========/
							/  タイプ  /
							/========*/
							for(int i=0;i<2;i++){
								tmp=st.nextToken();
								if(tmp.equals("-")){
									tmp="-1";
								}
								poke_builder.setType(i,Integer.parseInt(tmp));
							}
							//====================================================================================
							/*======/
							/  特性  /
							/======*/
							for(int i=0;i<3;i++){
								tmp=st.nextToken();
								if(tmp.equals("-")){
									tmp="-1";
								}
								poke_builder.setCharacter(i,Integer.parseInt(tmp));
							}
							//====================================================================================
							/*=========/
							/  進化条件  /
							/=========*/
							for(int i=0;i<num_evolutions;i++){
								poke_builder.addConditionEvolutions(st.nextToken());
							}
							//====================================================================================
							/*========/
							/  努力値  /
							/========*/
							for(int i=0;i<6;i++){
								poke_builder.setEff(i,Integer.valueOf(st.nextToken()));
							}
							//====================================================================================
							/*=================/
							/  持っているアイテム  /
							/=================*/
							for(int i=0;i<2;i++){
								poke_builder.setItem(i, st.nextToken());
							}
							//====================================================================================
							/*==============/
							/  タマゴグループ  /
							/==============*/
							for(int i=0;i<2;i++){
								tmp=st.nextToken();
								if(tmp.equals("-")){
									tmp="-1";
								}
								poke_builder.setEggGroup(i,Integer.parseInt(tmp));
							}
							//====================================================================================
							/*=========/
							/  孵化歩数  /
							/=========*/
							poke_builder.setHatchingStep(Integer.parseInt(st.nextToken()));
							//====================================================================================
							/*=========/
							/  性別比率  /
							/=========*/
							for(int i=0;i<2;i++){
								tmp=st.nextToken();
								if(tmp.equals("-")){
									tmp="-1";
								}
								poke_builder.setSexRatio(i,Integer.parseInt(tmp));
							}
							//====================================================================================
							/*============/
							/  捕獲しやすさ  /
							/============*/
							poke_builder.setEaseGet(Integer.parseInt(st.nextToken()));
							//====================================================================================
							/*============/
							/  初期なつき度  /
							/============*/
							poke_builder.setInitialNatsuki(Integer.parseInt(st.nextToken()));
							//====================================================================================
							/*===========/
							/  基礎経験値  /
							/===========*/
							poke_builder.setBasicEx(Integer.parseInt(st.nextToken()));
							//====================================================================================
							/*===========/
							/  最終経験値  /
							/===========*/
							poke_builder.setFinalEx(Integer.parseInt(st.nextToken()));
							//====================================================================================
							/*================/
							/  Height,Weight  /
							/================*/
							tmp=st.nextToken();
							if(tmp.equals("HW"))
								tmp=st.nextToken();
							//高さ
							poke_builder.setHeight(Integer.parseInt(tmp));
							//重さ
							poke_builder.setWeight(Integer.parseInt(st.nextToken()));
							//====================================================================================
							//====
							/*=========/
							/  覚える技  /
							/=========*/
							/*=============/
							/  Lvで覚える技  /
							/=============*/
							while(!(tmp=st.nextToken()).equals(DATA_LABEL[labelindex])){//タマゴわざまで
								poke_builder.addLearningLv(tmp);//覚えるレベル
								poke_builder.addLvSkill(Integer.valueOf(st.nextToken()));//覚える技
							}
							labelindex++;//DATA_LABELインデックスインクリメント

							//====================================================================================
							/*===========/
							/  タマゴわざ  /
							/===========*/
							while(!(tmp=st.nextToken()).equals(DATA_LABEL[labelindex])){//わざマシン
								poke_builder.addEggSkill(Integer.valueOf(tmp));
							}
							labelindex++;//DATA_LABELインデックスインクリメント

							//====================================================================================
							/*===========/
							/  わざマシン  /
							/===========*/
							while(!(tmp=st.nextToken()).equals(DATA_LABEL[labelindex])){//旧わざマシンまで
								if(tmp.charAt(0)=='H'){
									int tmp2=0;
									for(int i=1;i<tmp.length();i++){
										tmp2*=10;
										tmp2+=tmp.charAt(i)-'0';
									}
									poke_builder.addHiden(tmp2);
								}else{
									poke_builder.addMachine(Integer.valueOf(tmp));
								}
							}
							labelindex++;//DATA_LABELインデックスインクリメント
							//====================================================================================
							/*=============/
							/  旧わざマシン  /
							/=============*/
							while(!(tmp=st.nextToken()).equals(DATA_LABEL[labelindex])){//"教え技まで
								poke_builder.addOldMachine(Integer.valueOf(tmp));
							}
							labelindex++;//DATA_LABELインデックスインクリメント
							//====================================================================================
							/*==========/
							/  Pt教え技  /
							/==========*/
							while(!(tmp=st.nextToken()).equals(DATA_LABEL[labelindex])){//HS教え技
								poke_builder.addTeachSkillPt(Integer.valueOf(tmp));
							}
							labelindex++;//DATA_LABELインデックスインクリメント
							//====================================================================================
							/*==========/
							/  HS教え技  /
							/==========*/
							while(!(tmp=st.nextToken()).equals(DATA_LABEL[labelindex])){//BW教え技まで
								poke_builder.addTeachSkillHS(Integer.valueOf(tmp));
							}
							labelindex++;//DATA_LABELインデックスインクリメント
							//====================================================================================
							/*==========/
							/  BW教え技  /
							/==========*/
							while(st.hasMoreTokens()){
								poke_builder.addTeachSkillBW(Integer.valueOf(st.nextToken()));
							}
							//====================================================================================
							//poke_listに登録する
							poke_list.add(poke_builder.build());
						}
					}
				}
			}catch(IOException e){
			}finally{
				try{
					if(br!=null){
						br.close();
					}
				}catch(IOException e){
				}
			}
			return new PokeDataManager(poke_list.toArray(new PokeData[0]));
		}
		private PokeDataManager build(){
			return manager;//new PokeDataManager(poke_list.toArray(new PokeData[0]));
		}
	}
	
	//=========================================================================
	//ポケモンのデータ配列
	private final PokeData[] poke_data;
	private final Map<String,PokeData> name2poke_map=new HashMap<String,PokeData>();
	private final int num;
	public static final PokeData NullData=new PokeData.Builder().build();
	
	private PokeDataManager(PokeData[] poke_data){
		this.poke_data=poke_data;
		num=this.poke_data.length;
		for(PokeData poke:poke_data){	
			name2poke_map.put(poke.toString(), poke);
		}
	}
	//=========================================================================
	//ゲッター
	/**
	 * poke_dataの数を返す
	 */
	public int getNum(){
		return num;
	}
	
	public PokeData[] getAllData(){
		return poke_data.clone();
	}
	/**
	 * PokeDataを図鑑ナンバーから取得
	 * 該当しない場合はNullDataを返す
	 * @param no 図鑑ナンバー
	 * @return PokeData
	 */
	public PokeData getPokeData(int no){
		if(0<no && no<=num){
			return poke_data[no-1];
		}
		return NullData;
	}
	
	/**
	 * PokeDataを名前から取得
	 * 該当しない場合はNullDataを返す
	 * @param name　名前
	 * @return　PokeData
	 */
	public PokeData getPokeData(String name){
		PokeData poke=name2poke_map.get(name);
		if(poke==null){
			return NullData;
		}
		return poke;
	}

	//=========================================================================
}

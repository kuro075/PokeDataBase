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
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;

import kuro075.poke.pokedatabase.data_base.SearchTypes;
import kuro075.poke.pokedatabase.data_base.item.ItemData;
import kuro075.poke.pokedatabase.data_base.poke.PokeData.CharacterTypes;
import kuro075.poke.pokedatabase.data_base.poke.PokeData.EggGroups;
import kuro075.poke.pokedatabase.data_base.poke.PokeData.ItemRarities;
import kuro075.poke.pokedatabase.data_base.poke.PokeData.Sexes;
import kuro075.poke.pokedatabase.data_base.type.TypeDataManager;
import kuro075.poke.pokedatabase.data_base.type.TypeDataManager.TypeData;

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
	/*=========/
	/   enum   /
	/=========*/
	
	/**
	 * 表示可能な情報enum
	 * ・表示する情報を取得するメソッド
	 * ・ソート時に仕様するComparatorを取得するメソッド
	 */
	public enum ViewableInformations{
		TYPE("タイプ"){
			@Override
			public String getInformation(PokeData poke){
				TypeData type2=poke.getType(1);
				if(type2==null){
					return poke.getType(0).toString();
				}
				return poke.getType(0).toString()+" / "+type2.toString();
			}
			@Override
			public Comparator<PokeData> getComparator(){
				return new Comparator<PokeData>(){
					@Override
					public int compare(PokeData p1,PokeData p2){
						int p1_type=TypeDataManager.changeTypeToNum(p1.getType(0), p1.getType(1));
						int p2_type=TypeDataManager.changeTypeToNum(p2.getType(0), p2.getType(1));
						return p1_type-p2_type;
					}
				};
			}
		},
		CHARACTER1("特性1"){
			@Override
			public String getInformation(PokeData poke){
				//pokeからとくせい１を取得してCharacterDataで変換して返す
				return poke.getCharacter(CharacterTypes.FIRST).toString();
			}
			@Override
			public Comparator<PokeData> getComparator(){
				return new Comparator<PokeData>(){
					@Override
					public int compare(PokeData p1,PokeData p2){
						return p1.getCharacter(CharacterTypes.FIRST).compareTo(p2.getCharacter(CharacterTypes.FIRST));
					}
				};
			}
		},
		CHARACTER2("特性2"){
			@Override
			public String getInformation(PokeData poke){
				//pokeからとくせい2を取得してCharacterDataで変換して返す
				return poke.getCharacter(CharacterTypes.SECOND).toString();
			}
			@Override
			public Comparator<PokeData> getComparator(){
				return new Comparator<PokeData>(){
					@Override
					public int compare(PokeData p1,PokeData p2){
						return p1.getCharacter(CharacterTypes.SECOND).compareTo(p2.getCharacter(CharacterTypes.SECOND));
					}
				};
			}
		},
		DREAM_CHARACTER("夢特性"){
			@Override
			public String getInformation(PokeData poke){
				//pokeから夢特性を取得してCharacterDataで変換して返す
				return poke.getCharacter(CharacterTypes.DREAM).toString();
			}
			@Override
			public Comparator<PokeData> getComparator(){
				return new Comparator<PokeData>(){
					@Override
					public int compare(PokeData p1,PokeData p2){
						return p1.getCharacter(CharacterTypes.DREAM).compareTo(p2.getCharacter(CharacterTypes.DREAM));
					}
				};
			}
		},
		SPEC_ALL("種族値(全て)"){
			@Override
			public String getInformation(PokeData poke){
				StringBuilder sb=new StringBuilder();
				for(int i=0;i<6;i++){
					sb.append(poke.getSpec(i));
					if(i<5) sb.append(",");
				}
				return new String(sb);
			}

			@Override
			public Comparator<PokeData> getComparator() {
				return new Comparator<PokeData>(){
					@Override
					public int compare(PokeData p1, PokeData p2) {
						// TODO Auto-generated method stub
						return p1.getTotalSpec()-p2.getTotalSpec();
					}
				};
			}
		},
		SPEC_HP("種族値(HP)"){
			private final int spec_index=0;
			@Override
			public String getInformation(PokeData poke){
				return String.valueOf(poke.getSpec(spec_index));
			}

			@Override
			public Comparator<PokeData> getComparator() {
				return new Comparator<PokeData>(){
					@Override
					public int compare(PokeData p1, PokeData p2) {
						// TODO Auto-generated method stub
						return p1.getSpec(spec_index)-p2.getSpec(spec_index);
					}
				};
			}
		},
		SPEC_ATTACK("種族値(攻撃)"){
			private final int spec_index=1;
			@Override
			public String getInformation(PokeData poke){
				return String.valueOf(poke.getSpec(spec_index));
			}

			@Override
			public Comparator<PokeData> getComparator() {
				return new Comparator<PokeData>(){
					@Override
					public int compare(PokeData p1, PokeData p2) {
						// TODO Auto-generated method stub
						return p1.getSpec(spec_index)-p2.getSpec(spec_index);
					}
				};
			}
		},
		SPEC_BLOCK("種族値(防御)"){
			private final int spec_index=2;
			@Override
			public String getInformation(PokeData poke){
				return String.valueOf(poke.getSpec(spec_index));
			}

			@Override
			public Comparator<PokeData> getComparator() {
				return new Comparator<PokeData>(){
					@Override
					public int compare(PokeData p1, PokeData p2) {
						// TODO Auto-generated method stub
						return p1.getSpec(spec_index)-p2.getSpec(spec_index);
					}
				};
			}
		},
		SPEC_CONTACT("種族値(特殊)"){
			private final int spec_index=3;
			@Override
			public String getInformation(PokeData poke){
				return String.valueOf(poke.getSpec(spec_index));
			}

			@Override
			public Comparator<PokeData> getComparator() {
				return new Comparator<PokeData>(){
					@Override
					public int compare(PokeData p1, PokeData p2) {
						// TODO Auto-generated method stub
						return p1.getSpec(spec_index)-p2.getSpec(spec_index);
					}
				};
			}
		},
		SPEC_DEFENSE("種族値(特防)"){
			private final int spec_index=4;
			@Override
			public String getInformation(PokeData poke){
				return String.valueOf(poke.getSpec(spec_index));
			}

			@Override
			public Comparator<PokeData> getComparator() {
				return new Comparator<PokeData>(){
					@Override
					public int compare(PokeData p1, PokeData p2) {
						// TODO Auto-generated method stub
						return p1.getSpec(spec_index)-p2.getSpec(spec_index);
					}
				};
			}
		},
		SPEC_SPEED("種族値(素早)"){
			private final int spec_index=5;
			@Override
			public String getInformation(PokeData poke){
				return String.valueOf(poke.getSpec(spec_index));
			}

			@Override
			public Comparator<PokeData> getComparator() {
				return new Comparator<PokeData>(){
					@Override
					public int compare(PokeData p1, PokeData p2) {
						// TODO Auto-generated method stub
						return p1.getSpec(spec_index)-p2.getSpec(spec_index);
					}
				};
			}
		},
		SPEC_TOTAL("種族値(合計)"){
			private final int spec_index=6;
			@Override
			public String getInformation(PokeData poke){
				return String.valueOf(poke.getSpec(spec_index));
			}

			@Override
			public Comparator<PokeData> getComparator() {
				return new Comparator<PokeData>(){
					@Override
					public int compare(PokeData p1, PokeData p2) {
						// TODO Auto-generated method stub
						return p1.getSpec(spec_index)-p2.getSpec(spec_index);
					}
				};
			}
		},
		EFF_ALL("努力値(全て)"){
			@Override
			public String getInformation(PokeData poke){
				StringBuilder sb=new StringBuilder();
				for(int i=0;i<6;i++){
					final int eff=poke.getEff(i);
					if(eff>0){
						sb.append("+");
					}
					sb.append(poke.getEff(i));
					if(i<5) sb.append(",");
				}
				return new String(sb);
			}

			@Override
			public Comparator<PokeData> getComparator() {
				return new Comparator<PokeData>(){
					@Override
					public int compare(PokeData p1, PokeData p2) {
						// TODO Auto-generated method stub
						return p1.getTotalEff()-p2.getTotalEff();
					}
				};
			}
		},
		EFF_HP("努力値(HP)"){
			private final int spec_index=0;
			@Override
			public String getInformation(PokeData poke){
				final int eff=poke.getEff(spec_index);
				StringBuilder sb=new StringBuilder();
				if(eff>0){
					sb.append("+");
				}
				sb.append(eff);
				return new String(sb);
			}

			@Override
			public Comparator<PokeData> getComparator() {
				return new Comparator<PokeData>(){
					@Override
					public int compare(PokeData p1, PokeData p2) {
						// TODO Auto-generated method stub
						return p1.getEff(spec_index)-p2.getEff(spec_index);
					}
				};
			}
		},
		EFF_ATTACK("努力値(攻撃)"){
			private final int spec_index=1;
			@Override
			public String getInformation(PokeData poke){
				final int eff=poke.getEff(spec_index);
				StringBuilder sb=new StringBuilder();
				if(eff>0){
					sb.append("+");
				}
				sb.append(eff);
				return new String(sb);
			}

			@Override
			public Comparator<PokeData> getComparator() {
				return new Comparator<PokeData>(){
					@Override
					public int compare(PokeData p1, PokeData p2) {
						// TODO Auto-generated method stub
						return p1.getEff(spec_index)-p2.getEff(spec_index);
					}
				};
			}
		},
		EFF_BLOCK("努力値(防御)"){
			private final int spec_index=2;
			@Override
			public String getInformation(PokeData poke){
				final int eff=poke.getEff(spec_index);
				StringBuilder sb=new StringBuilder();
				if(eff>0){
					sb.append("+");
				}
				sb.append(eff);
				return new String(sb);
			}

			@Override
			public Comparator<PokeData> getComparator() {
				return new Comparator<PokeData>(){
					@Override
					public int compare(PokeData p1, PokeData p2) {
						// TODO Auto-generated method stub
						return p1.getEff(spec_index)-p2.getEff(spec_index);
					}
				};
			}
		},
		EFF_CONTACT("努力値(特殊)"){
			private final int spec_index=3;
			@Override
			public String getInformation(PokeData poke){
				final int eff=poke.getEff(spec_index);
				StringBuilder sb=new StringBuilder();
				if(eff>0){
					sb.append("+");
				}
				sb.append(eff);
				return new String(sb);
			}

			@Override
			public Comparator<PokeData> getComparator() {
				return new Comparator<PokeData>(){
					@Override
					public int compare(PokeData p1, PokeData p2) {
						// TODO Auto-generated method stub
						return p1.getEff(spec_index)-p2.getEff(spec_index);
					}
				};
			}
		},
		EFF_DIFENSE("努力値(特防)"){
			private final int spec_index=4;
			@Override
			public String getInformation(PokeData poke){
				final int eff=poke.getEff(spec_index);
				StringBuilder sb=new StringBuilder();
				if(eff>0){
					sb.append("+");
				}
				sb.append(eff);
				return new String(sb);
			}

			@Override
			public Comparator<PokeData> getComparator() {
				return new Comparator<PokeData>(){
					@Override
					public int compare(PokeData p1, PokeData p2) {
						// TODO Auto-generated method stub
						return p1.getEff(spec_index)-p2.getEff(spec_index);
					}
				};
			}
		},
		EFF_SPEED("努力値(素早)"){
			private final int spec_index=5;
			@Override
			public String getInformation(PokeData poke){
				final int eff=poke.getEff(spec_index);
				StringBuilder sb=new StringBuilder();
				if(eff>0){
					sb.append("+");
				}
				sb.append(eff);
				return new String(sb);
			}

			@Override
			public Comparator<PokeData> getComparator() {
				return new Comparator<PokeData>(){
					@Override
					public int compare(PokeData p1, PokeData p2) {
						// TODO Auto-generated method stub
						return p1.getEff(spec_index)-p2.getEff(spec_index);
					}
				};
			}
		},
		EFF_TOTAL("努力値(合計)"){
			private final int spec_index=6;
			@Override
			public String getInformation(PokeData poke){
				final int eff=poke.getEff(spec_index);
				StringBuilder sb=new StringBuilder();
				if(eff>0){
					sb.append("+");
				}
				sb.append(eff);
				return new String(sb);
			}

			@Override
			public Comparator<PokeData> getComparator() {
				return new Comparator<PokeData>(){
					@Override
					public int compare(PokeData p1, PokeData p2) {
						// TODO Auto-generated method stub
						return p1.getEff(spec_index)-p2.getEff(spec_index);
					}
				};
			}
		},
		MAX_PHYSICAL_DEFENSE("最大物理耐久"){
			@Override
			public String getInformation(PokeData poke) {
				// TODO Auto-generated method stub
				return String.valueOf(poke.getMaxPhysicalDefense());
			}

			@Override
			public Comparator<PokeData> getComparator() {
				// TODO Auto-generated method stub
				return new Comparator<PokeData>(){
					@Override
					public int compare(PokeData p1, PokeData p2) {
						// TODO Auto-generated method stub
						return p1.getMaxPhysicalDefense()-p2.getMaxPhysicalDefense();
					}
				};
			}
		},
		MAX_SPCIAL_DEFENSE("最大特殊耐久"){
			@Override
			public String getInformation(PokeData poke) {
				// TODO Auto-generated method stub
				return String.valueOf(poke.getMaxSpecialDefense());
			}

			@Override
			public Comparator<PokeData> getComparator() {
				// TODO Auto-generated method stub
				return new Comparator<PokeData>(){
					@Override
					public int compare(PokeData p1, PokeData p2) {
						// TODO Auto-generated method stub
						return p1.getMaxSpecialDefense()-p2.getMaxSpecialDefense();
					}
				};
			}
		},
		HEIGHT("高さ"){

			@Override
			public String getInformation(PokeData poke) {
				// TODO Auto-generated method stub
				StringBuilder sb=new StringBuilder();
				sb.append(poke.getHeight()/10);
				sb.append(".");
				sb.append(poke.getHeight()%10);
				sb.append("m");
				return new String(sb);
			}

			@Override
			public Comparator<PokeData> getComparator() {
				// TODO Auto-generated method stub
				return new Comparator<PokeData>(){
					@Override
					public int compare(PokeData p1, PokeData p2) {
						// TODO Auto-generated method stub
						return p1.getHeight()-p2.getHeight();
					}
				};
			}
		},
		WEIGHT("重さ"){
			@Override
			public String getInformation(PokeData poke) {
				// TODO Auto-generated method stub
				StringBuilder sb=new StringBuilder();
				sb.append(poke.getWeight()/10);
				sb.append(".");
				sb.append(poke.getWeight()%10);
				sb.append("kg");
				return new String(sb);
			}

			@Override
			public Comparator<PokeData> getComparator() {
				// TODO Auto-generated method stub
				return new Comparator<PokeData>(){
					@Override
					public int compare(PokeData p1, PokeData p2) {
						// TODO Auto-generated method stub
						return p1.getWeight()-p2.getWeight();
					}
				};
			}
		},
		EGG_GROUP("タマゴグループ"){
			@Override
			public String getInformation(PokeData poke) {
				// TODO Auto-generated method stub
				EggGroups egg_group2=poke.getEggGroup(1);
				if(egg_group2==null){
					return poke.getEggGroup(0).toString();
				}
				return poke.getEggGroup(0).toString()+" / "+egg_group2.toString();
			}

			@Override
			public Comparator<PokeData> getComparator() {
				// TODO Auto-generated method stub
				return new Comparator<PokeData>(){
					@Override
					public int compare(PokeData p1,PokeData p2){
						int p1_group=EggGroups.changeEggGroupToNum(p1.getEggGroup(0), p1.getEggGroup(1));
						int p2_group=EggGroups.changeEggGroupToNum(p2.getEggGroup(0), p2.getEggGroup(1));
						return p1_group-p2_group;
					}
				};
			}
		},
		HATCHING_STEP("孵化歩数"){
			@Override
			public String getInformation(PokeData poke) {
				// TODO Auto-generated method stub
				return poke.getHatchingStep().toString();
			}

			@Override
			public Comparator<PokeData> getComparator() {
				// TODO Auto-generated method stub
				return new Comparator<PokeData>(){
					@Override
					public int compare(PokeData p1,PokeData p2){
						return p1.getHatchingStep().getIndex()-p2.getHatchingStep().getIndex();
					}
				};
			}
		},
		SEX_RATIO("性別比率(♂:♀)"){
			@Override
			public String getInformation(PokeData poke) {
				// TODO Auto-generated method stub
				final int male=poke.getSexRatio(Sexes.MALE),
					female=poke.getSexRatio(Sexes.FEMALE);
				if(male<0){
					if(female<0){
						return "性別不明";
					}else{
						return "♀のみ";
					}
				}else if(female<0){
					return "♂のみ";
				}StringBuilder sb=new StringBuilder();
				sb.append(male);
				sb.append(":");
				sb.append(female);
				return new String(sb);
			}

			/**
			 * ♂:♀=1:1,1:4,1:8,4:1,8:1,♂のみ,♀のみ,性別不明
			 * の順になるように
			 */
			@Override
			public Comparator<PokeData> getComparator() {
				// TODO Auto-generated method stub
				return new Comparator<PokeData>(){
					@Override
					public int compare(PokeData p1,PokeData p2){
						final int p1_male=p1.getSexRatio(Sexes.MALE)<=0?10:p1.getSexRatio(Sexes.MALE),
								  p1_female=p1.getSexRatio(Sexes.FEMALE)<=0?10:p1.getSexRatio(Sexes.FEMALE);
						final int p2_male=p2.getSexRatio(Sexes.MALE)<=0?10:p2.getSexRatio(Sexes.MALE),
						          p2_female=p2.getSexRatio(Sexes.FEMALE)<=0?10:p2.getSexRatio(Sexes.FEMALE);
						if(p1_male==p2_male){
							return p1_female-p2_female;
						}
						return p1_male-p2_male;
					}
				};
			}
		},
		ITEM_COMMON("持っている道具(通常)"){
			@Override
			public String getInformation(PokeData poke) {
				// TODO Auto-generated method stub
				//ItemDataManagerから道具名を取ってきて返す
				final ItemData item=poke.getItem(ItemRarities.COMMON);
				if(item==null) return "-";
				return item.toString();
			}

			@Override
			public Comparator<PokeData> getComparator() {
				// TODO Auto-generated method stub
				return new Comparator<PokeData>(){
					@Override
					public int compare(PokeData p1,PokeData p2){
						final ItemData item1=p1.getItem(ItemRarities.COMMON);
						final ItemData item2=p2.getItem(ItemRarities.COMMON);
						final String name1=item1==null?"-":item1.toString();
						final String name2=item2==null?"-":item2.toString();
						return name1.compareTo(name2);
					}
				};
			}
		},
		ITEM_RARE("持っている道具(レア)"){
			@Override
			public String getInformation(PokeData poke) {
				// TODO Auto-generated method stub
				//ItemDataManagerから道具名を取ってきて返す
				final ItemData item=poke.getItem(ItemRarities.RARE);
				if(item==null) return "-";
				return item.toString();
			}

			@Override
			public Comparator<PokeData> getComparator() {
				// TODO Auto-generated method stub
				return new Comparator<PokeData>(){
					@Override
					public int compare(PokeData p1,PokeData p2){
						final ItemData item1=p1.getItem(ItemRarities.RARE);
						final ItemData item2=p2.getItem(ItemRarities.RARE);
						final String name1=item1==null?"-":item1.toString();
						final String name2=item2==null?"-":item2.toString();
						return name1.compareTo(name2);
					}
				};
			}
		},
		FINAL_EX("最終経験値"){
			@Override
			public String getInformation(PokeData poke) {
				// TODO Auto-generated method stub
				return poke.getFinalEx().toString();
			}

			@Override
			public Comparator<PokeData> getComparator() {
				// TODO Auto-generated method stub
				return new Comparator<PokeData>(){
					@Override
					public int compare(PokeData p1,PokeData p2){
						return p1.getFinalEx().getIndex()-p2.getFinalEx().getIndex();
					}
				};
			}
		},
		EASE_GET("つかまえやすさ"){
			@Override
			public String getInformation(PokeData poke) {
				// TODO Auto-generated method stub
				return String.valueOf(poke.getEaseGet());
			}

			@Override
			public Comparator<PokeData> getComparator() {
				// TODO Auto-generated method stub
				return new Comparator<PokeData>(){
					@Override
					public int compare(PokeData p1,PokeData p2){
						return p1.getEaseGet()-p2.getEaseGet();
					}
				};
			}
		},
		BASIC_EX("基礎経験値"){
			@Override
			public String getInformation(PokeData poke) {
				// TODO Auto-generated method stub
				return String.valueOf(poke.getBasicEx());
			}

			@Override
			public Comparator<PokeData> getComparator() {
				// TODO Auto-generated method stub
				return new Comparator<PokeData>(){
					@Override
					public int compare(PokeData p1,PokeData p2){
						return p1.getBasicEx()-p2.getBasicEx();
					}
				};
			}
		},
		INITIAL_NATSUKI("初期なつき"){
			@Override
			public String getInformation(PokeData poke) {
				// TODO Auto-generated method stub
				return String.valueOf(poke.getInitialNatsuki());
			}

			@Override
			public Comparator<PokeData> getComparator() {
				// TODO Auto-generated method stub
				return new Comparator<PokeData>(){
					@Override
					public int compare(PokeData p1,PokeData p2){
						return p1.getInitialNatsuki()-p2.getInitialNatsuki();
					}
				};
			}
		};
		
		
		//====================================================================
		private final String name;
		ViewableInformations(String name){
			this.name=name;
		}
		@Override
		public String toString(){
			return name;
		}
		//表示する情報を取得
		abstract public String getInformation(PokeData poke);
		/**
		 * ソートするときのComparatorを取得
		 * 一つ目の引数がが二つ目の引数より小さければ負の整数、同じであれば０、大きければ正の整数を戻す。
		 * 
		 * @return
		 */
		abstract public Comparator<PokeData> getComparator();
	}
	
	
	/**
	 * 絞り込み、追加、除外を行える情報のenum
	 * ・検索を行うメソッド（渡されたPokeData[]から条件に合うポケを検索して返す）
	 * ・それぞれのダイアログを取得するメソッド(選ばれた検索条件から検索されたPokeData[]をSearchedPokeListenerに投げる)
	 * ・詳細条件に登録する文字列を取得するメソッド
	 * ・詳細条件から検索を行うメソッド
	 * @author sanogenma
	 *
	 */
	public enum SearchableInformations{
		NO("図鑑No") {
			@Override
			public AlertDialog getDialog(Context context, PokeData[] pokes,
					SearchTypes search_type, SearchedPokeListener listener) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public PokeData[] search(PokeData[] poke_array, String condition) {
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
		 * @param pokes
		 * @param search_type
		 * @param listener
		 * @return
		 */
		abstract public AlertDialog getDialog(Context context,PokeData[] pokes,SearchTypes search_type,SearchedPokeListener listener);
		/**
		 * 検索条件(文字列)からポケモンを検索して返すメソッド
		 * @param condition　検索条件
		 * @return
		 */
		abstract public PokeData[] search(PokeData[] poke_array,String condition);
		/**
		 * condition=種類:hoge(SearchTypes)
		 * @param poke_array
		 * @param condtion
		 * @return
		 */
		public static PokeData[] searchByCondition(PokeData[] poke_array,String condition){
			String[] tmp=condition.split(":()");
			return fromString(tmp[0]).search(poke_array, tmp[1]);
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
	//=========================================================================
	//ポケモンのデータ配列
	private final PokeData[] poke_data;
	private final String[] poke_name;
	private final int num;
	public static final PokeData NullData=new PokeData.Builder().build();
	
	private PokeDataManager(PokeData[] poke_data){
		this.poke_data=poke_data;
		num=this.poke_data.length;
		poke_name=new String[num];
		for(int i=0;i<num;i++){	
			this.poke_name[i]=poke_data[i].toString();
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
	/**
	 * PokeDataを図鑑ナンバーから取得
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
	 * @param name　名前
	 * @return　PokeData
	 */
	public PokeData getPokeData(String name){
		final int no=Arrays.binarySearch(poke_name, name)+1;
		return getPokeData(no);
	}

	//=========================================================================
}

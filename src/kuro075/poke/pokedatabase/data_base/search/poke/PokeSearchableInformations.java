package kuro075.poke.pokedatabase.data_base.search.poke;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import kuro075.poke.pokedatabase.R;
import kuro075.poke.pokedatabase.data_base.SearchIfListener;
import kuro075.poke.pokedatabase.data_base.SearchTypes;
import kuro075.poke.pokedatabase.data_base.character.CharacterData;
import kuro075.poke.pokedatabase.data_base.character.CharacterDataManager;
import kuro075.poke.pokedatabase.data_base.item.ItemDataManager;
import kuro075.poke.pokedatabase.data_base.poke.PokeData;
import kuro075.poke.pokedatabase.data_base.poke.PokeDataManager;
import kuro075.poke.pokedatabase.data_base.poke.PokeData.EggGroups;
import kuro075.poke.pokedatabase.data_base.search.PokeSearchIfCategory;
import kuro075.poke.pokedatabase.data_base.search.SearchIf;
import kuro075.poke.pokedatabase.data_base.skill.SkillData;
import kuro075.poke.pokedatabase.data_base.skill.SkillDataManager;
import kuro075.poke.pokedatabase.data_base.skill.SkillData.SkillClasses;
import kuro075.poke.pokedatabase.data_base.type.TypeDataManager.TypeData;
import kuro075.poke.pokedatabase.util.Utility;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public enum PokeSearchableInformations  implements PokeSearchIfCategory{
	NAME("名前"){
		@Override
		public String getDefaultSearchIf(String head) {
			StringBuilder sb=new StringBuilder();
			sb.append(head);
			sb.append(" ");
			sb.append(NameSearchOptions.START.toString());
			return SearchIf.createSearchIf(NAME,new String(sb) , SearchTypes.FILTER);
		}
		@Override
		public String getDefaultTitle(String head) {
			return head+NameSearchOptions.START;
		}

		@Override
		public void openDialog(final Context context, final SearchTypes search_type,
				final SearchIfListener listener) {
			Utility.log(toString(),"openDialog");
			AlertDialog.Builder builder;
			LayoutInflater factory=LayoutInflater.from(context);
			
			final View layout = factory.inflate(R.layout.string_input_dialog,null);
			builder = new AlertDialog.Builder(context);
			builder.setTitle(SearchIf.createDialogTitle(this, search_type));
			builder.setView(layout);
			
			
			final EditText edit=(EditText)layout.findViewById(R.id.edit);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,R.layout.center_spinner_item,Utility.changeToStringArray(NameSearchOptions.values()));
			final Spinner spinner = (Spinner) layout.findViewById(R.id.spinner);
			spinner.setAdapter(adapter);
			((TextView)layout.findViewById(R.id.text)).setText("※ひらがな、カタカナのみ有効(ひらがなは、カタカナに自動変換されます)");

			builder.setPositiveButton("検索",new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					String text=Utility.changeHiraToKata(edit.getText().toString());
					if(text.equals("")){
						Utility.popToast(context, "無効な文字列です");
						listener.receiveSearchIf(null);
					}else{
						StringBuilder sb=new StringBuilder();
						sb.append(text);
						sb.append(" ");
						sb.append(NameSearchOptions.values()[spinner.getSelectedItemPosition()]);
						listener.receiveSearchIf(SearchIf.createSearchIf(NAME,new String(sb),search_type));
					}
				}
			});
			builder.setNegativeButton("戻る",new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
					listener.receiveSearchIf(null);
				}
			});
			
			builder.create().show();
		}

		/**
		 * @param poke_array
		 * @param category
		 * @param _case : "カタカナの文字列 NameSearchOption"
		 * 
		 */
		@Override
		public PokeData[] search(PokeData[] poke_array, String category,
				String _case) {
			if(toString().equals(category)){
				String[] text_option=_case.split(" ");
				NameSearchOptions option=NameSearchOptions.fromString(text_option[1]);
				List<PokeData> poke_list=new ArrayList<PokeData>();
				for(PokeData poke:poke_array){
					if(option.compareOf(poke, text_option[0])){
						poke_list.add(poke);
					}
				}
				return poke_list.toArray(new PokeData[0]);
			}
			return new PokeData[0];
		}
		/**
		 * フリーワードから検索条件を取得
		 * 該当文字列)五文字以下の平仮名・カタカナの文字列、またはその文字列と「から始まる」「からはじまる」「を含む」「をふくむ」「で終わる」「でおわる」のいずれかが続く文字列
		 * @param free_word
		 * @return _case or ""
		 */
		@Override
		public String getCaseByFreeWord(String free_word) {
			// TODO Auto-generated method stub
			int length=free_word.length();
			//free_wordが「から始まる」「からはじまる」「を含む」「をふくむ」「で終わる」「でおわる」を含むかどうかチェック(NameSearchOptionsの各項目のtoString(),getHiraganaName()と一致するか)
			NameSearchOptions option=NameSearchOptions.fromString(free_word);
			//含まない場合はデフォルトとして「を含む」とする
			if(option==null){
				option=NameSearchOptions.INVOLVE;
			}else{
				length=free_word.indexOf(option.toString());
				if(length<=0){
					length=free_word.indexOf(option.getHiraganaName());
				}
			}
			//検索ワードが５文字以下か、平仮名・カタカナのみかをチェック　該当しない場合""を返す
			if(0<length && length<=5){
				StringBuilder sb=new StringBuilder();
				for(int i=0;i<length;i++){
					sb.append(free_word.charAt(i));
				}
				String text=Utility.changeHiraToKata(new String(sb));
				sb=new StringBuilder();
				sb.append(text);
				sb.append(" ");
				sb.append(option);
				//_caseを返す
				return new String(sb);
			}
			return "";
		}
	},
	REGION("地方") {
		/*===================================/
		 * 地方で検索
		/==================================*/
		private final String[] REGION_NAMES={"カントー","ジョウト","ホウエン","シンオウ","イッシュ"};
		private final String[] GENERATION_NAMES={"第一世代","第二世代","第三世代","第四世代","第五世代"};
		private final String[] GENERATION_NAMES2={"第1世代","第2世代","第3世代","第4世代","第5世代"};
		private final String[] GENERATION_NAMES3={"第１世代","第２世代","第３世代","第４世代","第５世代"};
		private final int[] FIRST_NO={1,152,252,387,494,650};
		
		@Override
		public void openDialog(Context context,
				SearchTypes search_type, SearchIfListener listener) {
			Utility.log(toString(),"openDialog");
			AlertDialog.Builder builder;
			LayoutInflater factory=LayoutInflater.from(context);
			
			final View layout = factory.inflate(R.layout.simple_list_dialog,null);
			builder = new AlertDialog.Builder(context);
			builder.setTitle(SearchIf.createDialogTitle(this, search_type));
			builder.setView(layout);
			
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,R.layout.center_list_item,REGION_NAMES);
			final ListView listView = (ListView) layout.findViewById(R.id.list_view);
			listView.setAdapter(adapter);
			
			final SearchIfListener final_listener=listener;
			final SearchTypes final_search_type=search_type;

			builder.setPositiveButton("戻る",new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
					final_listener.receiveSearchIf(null);
				}
			});
			
			final AlertDialog dialog=builder.create();
			listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
					Utility.log(toString(),"onItemClick");
					final_listener.receiveSearchIf(SearchIf.createSearchIf(REGION,REGION_NAMES[position],final_search_type));
					dialog.dismiss();
				}
			});
			dialog.show();
		}
		@Override
		public PokeData[] search(PokeData[] poke_array, String category,
				String region) {
			if(toString().equals(category)){
				int first=0,last=0;
				for(int i=0,n=REGION_NAMES.length;i<n;i++){
					if(REGION_NAMES[i].equals(region)){
						first=FIRST_NO[i];
						last=FIRST_NO[i+1];
						break;
					}
				}
				List<PokeData> list=new ArrayList<PokeData>();
				for(PokeData poke:poke_array){
					if(first<=poke.getNo() && poke.getNo()<last){
						list.add(poke);
					}
				}
				return list.toArray(new PokeData[0]);
			}
			return new PokeData[0];
		}
		/**
		 * フリーワードから検索条件(_case)を取得
		 * 該当文字列)REGION_NAMESにある文字列、第一世代、第二世代、第三世代、第４世代、第５世代
		 */
		@Override
		public String getCaseByFreeWord(String free_word) {
			// TODO Auto-generated method stub
			int index=free_word.indexOf("地方");
			String word=null;
			if(index>0){
				StringBuilder sb=new StringBuilder();
				for(int i=0;i<index;i++){
					sb.append(free_word.charAt(i));
				}
				word=new String(sb);
			}else{
				word=free_word;
			}
			//free_wordがREGION_NAMEにあるかどうかをチェックしてあったら_caseを返す
			//free_wordが第一世代〜第五世代のいずれかである場合_caseを返す
			for(int i=0;i<REGION_NAMES.length;i++){
				if(word.equals(REGION_NAMES[i]) ||
				   word.equals(GENERATION_NAMES[i]) ||
				   word.equals(GENERATION_NAMES2[i]) ||
				   word.equals(GENERATION_NAMES3[i])){
					return REGION_NAMES[i];
				}
			}
			//それ以外
			return "";
		}
	},
	TYPE("タイプ"){
		/*=======================/
		 * タイプで検索
		 * ・MYSELF(タイプ) default
		 * ・RELATION(タイプ相性)
		/=======================*/
		@Override
		public String getDefaultSearchIf(String type) {
			return SearchIf.createSearchIf(TypeCategories.MYSELF,type,SearchTypes.FILTER);
		}
		@Override
		public boolean isCategory(String category) {
			for(TypeCategories type:TypeCategories.values()){
				if(type.toString().equals(category)){
					return true;
				}
			}
			return false;
		}
		@Override
		public void openDialog(Context context,
				SearchTypes search_type, SearchIfListener listener) {
			TypeCategories.MYSELF.openDialog(context, search_type, listener);
		}
		@Override
		public PokeData[] search(PokeData[] poke_array, String category,
				String type) {
			return TypeCategories.fromString(category).search(poke_array, category, type);
		}
		/**
		 * フリーワードから_caseを取得
		 * 該当文字列)タイプ名のみ、タイプ名＋"タイプ"(TypeCategories.MYSELF)　　タイプ名+"が"+相性、タイプ名+"タイプが"+相性(TypeCategories.RELATION)
		 */
		@Override
		public String getCaseByFreeWord(String free_word) {
			// TODO Auto-generated method stub
			//TypeCategories.MYSELF.getCaseByFreeWordを呼び出し、""でなければ_caseを返す
			
			//TypeCategories.RELATION.getCaseByFreeWord()を呼び出し、""でなければ_caseを返す
			
			//それ以外
			return "";
		}
	},
	CHARACTER("特性"){
		final String[] KANA={"","あ","か","さ","た","な","は","ま","や","ら","わ"};
		final String[] JOUKEN={"通常","夢"};
		@Override
		public void openDialog(final Context context,
				final SearchTypes search_type,final SearchIfListener listener) {
			//SearchIf.createSimpleSpinnerDialogBuilder(context,search_type,listener,this,CharacterDataManager.INSTANCE.getAllCharaName()).create().show();
			
			LayoutInflater factory=LayoutInflater.from(context);
			final View layout = factory.inflate(R.layout.dialog_select_character,null);
			
			//通常
			final CheckBox check_normal=(CheckBox)layout.findViewById(R.id.check_normal);
			//夢特性
			final CheckBox check_dream=(CheckBox)layout.findViewById(R.id.check_dream);
			
			ArrayAdapter<String> adapter;
			//特性頭文字スピナー設定
			final Spinner spinner_head = (Spinner)layout.findViewById(R.id.spinner_head);
			adapter = new ArrayAdapter<String>(context,R.layout.center_spinner_item,KANA);
			spinner_head.setAdapter(adapter);

			final Spinner spinner_charaname = (Spinner)layout.findViewById(R.id.spinner_charaname);
			
			String[] all_chara_name=CharacterDataManager.INSTANCE.getAllCharaName();
			//特性名スピナー設定
			adapter = new ArrayAdapter<String>(context,R.layout.center_spinner_item,all_chara_name);
			spinner_charaname.setAdapter(adapter);
			
			spinner_head.setOnItemSelectedListener(new OnItemSelectedListener(){
				@Override
				public void onItemSelected(AdapterView<?> parent, View view, int position,long id) {
					if(position>0){
						List<String> chara_list=new ArrayList<String>();
						for(CharacterData chara:CharacterDataManager.INSTANCE.getAllData()){
							for(String head:Utility.getAiueoLine(position-1)){
								if(chara.isNameHead(head)){
									chara_list.add(chara.getName());
									break;
								}
							}
						}
						spinner_charaname.setAdapter(new ArrayAdapter<String>(context,R.layout.center_spinner_item,chara_list.toArray(new String[0])));
					}else{
						spinner_charaname.setAdapter(new ArrayAdapter<String>(context,R.layout.center_spinner_item,CharacterDataManager.INSTANCE.getAllCharaName()));
					}
				}
				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
				}
			});
			
			AlertDialog.Builder builder=new AlertDialog.Builder(context);
			builder.setTitle(SearchIf.createDialogTitle(this, search_type));
			builder.setView(layout);
			//検索
			builder.setPositiveButton("検索", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if(spinner_charaname.isEnabled() && (check_dream.isChecked() || check_normal.isChecked())){
						// TODO 検索
						StringBuilder sb=new StringBuilder();
						sb.append(spinner_charaname.getSelectedItem().toString());
						sb.append(" ");
						if(check_normal.isChecked()){
							sb.append(JOUKEN[0]);
							if(check_dream.isChecked()){
								sb.append("・");
								sb.append(JOUKEN[1]);
							}
						}else
						if(check_dream.isChecked()){
							sb.append(JOUKEN[1]);
						}
						listener.receiveSearchIf(SearchIf.createSearchIf(CHARACTER,new String(sb),search_type));
					}else{
						Utility.popToast(context,"検索できません");
					}
				}
			});
			builder.create().show();
		
		}

		@Override
		public PokeData[] search(PokeData[] poke_array, String category,
				String _case) {
			if(toString().equals(category)){
				String[] chara_and_jouken=_case.split(" ");
				CharacterData chara=CharacterDataManager.INSTANCE.getCharacterData(chara_and_jouken[0]);
				String[] jouken=chara_and_jouken[1].split("・");
				List<PokeData> list=new ArrayList<PokeData>();
				if(jouken.length==2){
					for(PokeData poke:poke_array){
						if(poke.hasCharacter(chara)) list.add(poke);
					}
				}else
				if(jouken[0].equals(JOUKEN[0])){//通常
					for(PokeData poke:poke_array){
						if(poke.getCharacter(0).equals(chara) || poke.getCharacter(1).equals(chara))
							list.add(poke);
					}
				}else
				if(jouken[1].equals(JOUKEN[1])){//夢特性
					for(PokeData poke:poke_array){
						if(poke.getCharacter(2).equals(chara))
							list.add(poke);
					}
				}
				return list.toArray(new PokeData[0]);
			}
			return new PokeData[0];
		}

		/**
		 * フリーワードから検索条件(_case)を返す
		 * 該当文字列)特性名
		 */
		@Override
		public String getCaseByFreeWord(String free_word) {
			// TODO Auto-generated method stub
			//free_wordが特性名だったら_caseを返す
			
			
			//それ以外
			return "";
		}
	},
	SPEC("種族値"){
		@Override
		public boolean isCategory(String category) {
			for(SpecCategories spec:SpecCategories.values()){
				if(spec.toString().equals(category))
					return true;
			}
			return false;
		}

		@Override
		public void openDialog(final Context context,
				final SearchTypes search_type,final SearchIfListener listener) {
			Utility.log(toString(),"openDialog");
			AlertDialog.Builder builder;
			LayoutInflater factory=LayoutInflater.from(context);
			
			final View layout = factory.inflate(R.layout.simple_list_dialog,null);
			builder = new AlertDialog.Builder(context);
			builder.setTitle(SearchIf.createDialogTitle(this, search_type));
			builder.setView(layout);
			
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,R.layout.center_list_item,Utility.changeToStringArray(SpecCategories.values()));
			final ListView listView = (ListView) layout.findViewById(R.id.list_view);
			listView.setAdapter(adapter);

			builder.setPositiveButton("戻る",new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// 
					listener.receiveSearchIf(null);
				}
			});
			
			final AlertDialog dialog=builder.create();
			listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
					// 
					Utility.log(toString(),"onItemClick");
					SpecCategories.values()[position].openDialog(context, search_type, listener);
					dialog.dismiss();
				}
			});
			dialog.show();
		}

		@Override
		public PokeData[] search(PokeData[] poke_array, String category,
				String _case) {
			for(SpecCategories spec:SpecCategories.values()){
				if(spec.toString().equals(category)){
					return spec.search(poke_array, category, _case);
				}
			}
			return new PokeData[0];
		}

		/**
		 * フリーワードから検索条件(_case)を返す
		 * 該当文字列)"Hが100","攻撃が200以上","防御<50","C≧100","Dが100より大きい","HがDより大きい","種族値が100"
		 */
		@Override
		public String getCaseByFreeWord(String free_word) {
			// TODO Auto-generated method stub
			return "";
		}
	},
	EFF("努力値"){
		@Override
		public boolean isCategory(String category) {
			// 
			for(EffCategories eff:EffCategories.values()){
				if(eff.toString().equals(category))
					return true;
			}
			return false;
		}

		@Override
		public void openDialog(final Context context,
				final SearchTypes search_type,final SearchIfListener listener) {
			Utility.log(toString(),"openDialog");
			AlertDialog.Builder builder;
			LayoutInflater factory=LayoutInflater.from(context);
			
			final View layout = factory.inflate(R.layout.simple_list_dialog,null);
			builder = new AlertDialog.Builder(context);
			builder.setTitle(SearchIf.createDialogTitle(this, search_type));
			builder.setView(layout);
			
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,R.layout.center_list_item,Utility.changeToStringArray(EffCategories.values()));
			final ListView listView = (ListView) layout.findViewById(R.id.list_view);
			listView.setAdapter(adapter);

			builder.setPositiveButton("戻る",new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
					listener.receiveSearchIf(null);
				}
			});
			
			final AlertDialog dialog=builder.create();
			listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
					Utility.log(toString(),"onItemClick");
					EffCategories.values()[position].openDialog(context, search_type, listener);
					dialog.dismiss();
				}
			});
			dialog.show();
		}

		@Override
		public PokeData[] search(PokeData[] poke_array, String category,
				String _case) {
			for(EffCategories eff:EffCategories.values()){
				if(eff.toString().equals(category)){
					return eff.search(poke_array, category, _case);
				}
			}
			return new PokeData[0];
		}

		/**
		 * フリーワードから検索条件(_case)を返す
		 * 該当文字列)
		 */
		@Override
		public String getCaseByFreeWord(String free_word) {
			// TODO Auto-generated method stub
			return "";
		}
	},
	SKILL("わざ"){
		public final String[] LEARNING_TYPE={"Lv","マ","卵","教"};
		public List<String> class_list=null,type_list=null;
		@Override
		public void openDialog(final Context context,final  SearchTypes search_type,
				final SearchIfListener listener) {
			//SearchIf.createSimpleSpinnerDialogBuilder(context, search_type, listener, this, SkillDataManager.INSTANCE.getAllSkillName()).create().show();
			LayoutInflater factory=LayoutInflater.from(context);
			final View layout = factory.inflate(R.layout.dialog_skillfilter,null);
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setTitle(SearchIf.createDialogTitle(this, search_type));
			builder.setView(layout);
			
			ArrayAdapter<String> adapter;
			
			//分類スピナーの設定
			final Spinner spinner_class = (Spinner)layout.findViewById(R.id.spinner_skillclass);
			if(class_list==null){
				class_list = new ArrayList<String>();
				class_list.add("");
				for(SkillClasses skill_class:SkillClasses.values()){
					class_list.add(skill_class.toString());
				}
			}
			adapter = new ArrayAdapter<String>(context,R.layout.center_spinner_item,class_list);
			spinner_class.setAdapter(adapter);
			
			//タイプスピナーの設定
			final Spinner spinner_type = (Spinner)layout.findViewById(R.id.spinner_type);
			if(type_list==null){
				type_list = new ArrayList<String>();
				type_list.add("");
				for(TypeData type:TypeData.values()){
					type_list.add(type.toString());
				}
			}
			adapter = new ArrayAdapter<String>(context,R.layout.center_spinner_item,type_list);
			spinner_type.setAdapter(adapter);
			//覚える技のスピナーの設定
			final Spinner spinner_name = (Spinner)layout.findViewById(R.id.spinner_skillname);
			final List<String> skill_list=new ArrayList<String>();
			skill_list.addAll(Arrays.asList(SkillDataManager.INSTANCE.getAllSkillName()));
			adapter = new ArrayAdapter<String>(context,R.layout.center_spinner_item,skill_list);
			spinner_name.setAdapter(adapter);
			
			final AdapterView.OnItemSelectedListener adapter_listener=new AdapterView.OnItemSelectedListener(){
				@Override
				public void onItemSelected(AdapterView<?> parent, View view, int position,long id) {
					List<String> list=new ArrayList<String>();
					//タイプと分類を考慮して技リストを作成
					TypeData type=TypeData.fromString(type_list.get(spinner_type.getSelectedItemPosition()));
					SkillClasses skill_class=SkillClasses.fromString(class_list.get(spinner_class.getSelectedItemPosition()));
					for(SkillData skill:SkillDataManager.INSTANCE.getAllData()){
						if(type==null || skill.getType()==type){//タイプが一致
							if(skill_class==null || skill.getSkillClass()==skill_class){
								list.add(skill.getName());
							}
						}
					}
					for(int i=0,n=skill_list.size();i<n;i++) skill_list.remove(0);
					skill_list.addAll(list);
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,R.layout.center_spinner_item,skill_list.toArray(new String[0]));
					spinner_name.setAdapter(adapter);
					spinner_name.setEnabled(skill_list.size()>0);
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
			};
			
			
			//分類が変更された時の動作
			spinner_class.setOnItemSelectedListener(adapter_listener);
			//タイプが変更された時の動作
			spinner_type.setOnItemSelectedListener(adapter_listener);
			//各チェックボックス
			final CheckBox[] check_box=new CheckBox[4];
			check_box[0]=(CheckBox)layout.findViewById(R.id.check_lv);
			check_box[1]=(CheckBox)layout.findViewById(R.id.check_machine);
			check_box[2]=(CheckBox)layout.findViewById(R.id.check_egg);
			check_box[3]=(CheckBox)layout.findViewById(R.id.check_teach);
			
			//検索
			builder.setPositiveButton("検索", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if(spinner_name.isEnabled()){
						boolean flag=false;
						boolean[] check_array=new boolean[4];
						for(int i=0;i<4;i++){
							check_array[i]=check_box[i].isChecked();
							flag=flag||check_array[i];
						}
						if(flag){
							//TODO 検索
							listener.receiveSearchIf(getSearchIf(SkillDataManager.INSTANCE.getSkillData(skill_list.get(spinner_name.getSelectedItemPosition())),check_array,search_type));
						}
					}else{
						Utility.popToast(context,"検索できません");
					}
				}
			});
			((TableLayout)layout.findViewById(R.id.table_check)).setStretchAllColumns(true);
			builder.create().show();
		}

		/**
		 * 技(skill),どうやって覚えるか(check),検索タイプ(search_type)
		 * @param skill
		 * @param check
		 * @param search_type
		 * @return
		 */
		private String getSearchIf(SkillData skill,boolean[] check,SearchTypes search_type){
			boolean flag=false;
			StringBuilder sb=new StringBuilder();
			sb.append(skill);
			sb.append(" ");
			for(int i=0;i<4;i++){
				if(check[i]){
					if(flag){
						sb.append("・");
					}
					sb.append(LEARNING_TYPE[i]);
					flag=true;
				}
			}
			return SearchIf.createSearchIf(this,new String(sb),search_type);
		}
		
		@Override
		public String getDefaultSearchIf(String skill_name){
			boolean[] flag=new boolean[4];
			Arrays.fill(flag,true);
			return getSearchIf(SkillDataManager.INSTANCE.getSkillData(skill_name),flag,SearchTypes.FILTER);
		}
		@Override
		public String getDefaultTitle(String _case){
			Utility.log(TAG, "getDefaultTitle");
			//skill_name lv マシン　タマゴ　教え　に分ける
			String[] skill_name_and_learning_type=_case.split(" ");
			StringBuilder sb = new StringBuilder();
			sb.append("「");
			sb.append(skill_name_and_learning_type[0]);
			sb.append("」を");
			sb.append(skill_name_and_learning_type[1]);
			sb.append("で覚えるポケモン");
			return new String(sb);
		}
		
		@Override
		public PokeData[] search(PokeData[] poke_array, String category,
				String _case) {
			if(toString().equals(category)){
				List<PokeData> list=new ArrayList<PokeData>();
				//skill_name lv マシン　タマゴ　教え　に分ける
				String[] skill_name_and_learning_type=_case.split(" ");
				SkillData skill=SkillDataManager.INSTANCE.getSkillData(skill_name_and_learning_type[0]);
				String[] learning_types=skill_name_and_learning_type[1].split("・");
				//lv マシン タマゴ 教えの全ての場合
				if(learning_types.length==4){
					for(PokeData poke:poke_array){
						if(poke.hasSkill(skill)){
							list.add(poke);
						}
					}
				}else{
					boolean[] flag=new boolean[4];
					Arrays.fill(flag,false);
					for(int i=0,n=learning_types.length;i<n;i++){
						for(int j=0,m=LEARNING_TYPE.length;j<m;j++){
							if(LEARNING_TYPE[j].equals(learning_types[i])){
								flag[j]=true;
								break;
							}
						}
					}
					for(PokeData poke:poke_array){
						if(flag[0]){//レベル技
							if(poke.hasSkillByLvSkill(skill)){
								list.add(poke);
								continue;
							}
						}
						if(flag[1]){//マシン技
							if(poke.hasSkillByMachine(skill)){
								list.add(poke);
								continue;
							}
						}
						if(flag[2]){//タマゴ技
							if(poke.hasSkillByEggSkill(skill)){
								list.add(poke);
								continue;
							}
						}
						if(flag[3]){//教え技
							if(poke.hasSkillByTeachSkill(skill)){
								list.add(poke);
								continue;
							}
						}
					}
				}
				return list.toArray(new PokeData[0]);
			}
			return new PokeData[0];
		}

		/**
		 * フリーワードから検索条件(_case)を返す
		 * 該当文字列)
		 */
		@Override
		public String getCaseByFreeWord(String free_word) {
			// TODO Auto-generated method stub
			return "";
		}
	},
	EGG_GROUP("タマゴグループ"){
		@Override
		public void openDialog(Context context,
				SearchTypes search_type, SearchIfListener listener) {
			// 
			SearchIf.createSimpleSpinnerDialogBuilder(context,search_type,listener,this,Utility.changeToStringArray(PokeData.EggGroups.values())).create().show();
		}

		@Override
		public PokeData[] search(PokeData[] poke_array, String category,
				String egg_group) {
			// 
			if(toString().equals(category)){
				List<PokeData> searched_list=new ArrayList<PokeData>();
				for(PokeData poke:poke_array){
					if(poke.hasEggGroup(EggGroups.fromString(egg_group))){
						searched_list.add(poke);
					}
				}
				return searched_list.toArray(new PokeData[0]);
			}
			return new PokeData[0];
		}

		/**
		 * フリーワードから検索条件(_case)を返す
		 * 該当文字列)
		 */
		@Override
		public String getCaseByFreeWord(String free_word) {
			// TODO Auto-generated method stub
			return "";
		}
		
	},
	HATCHING_STEP("孵化歩数"){
		@Override
		public void openDialog(Context context,
				SearchTypes search_type, SearchIfListener listener) {
			SearchIf.createSimpleSpinnerDialogBuilder(context,search_type,listener,this,PokeData.HatchingSteps.toStringArray()).create().show();
		}

		@Override
		public PokeData[] search(PokeData[] poke_array, String category,
				String step) {
			// 
			if(toString().equals(category)){
				List<PokeData> searched_list=new ArrayList<PokeData>();
				for(PokeData poke:poke_array){
					if(poke.getHatchingStep().toString().equals(step)) 
						searched_list.add(poke);
				}
				return searched_list.toArray(new PokeData[0]);
			}
			return new PokeData[0];
		}

		/**
		 * フリーワードから検索条件(_case)を返す
		 * 該当文字列)
		 */
		@Override
		public String getCaseByFreeWord(String free_word) {
			// TODO Auto-generated method stub
			return "";
		}
		
	},
	ITEM("アイテム"){
		@Override
		public String getDefaultTitle(String item_name) {
			// 
			StringBuilder sb=new StringBuilder();
			sb.append("「");
			sb.append(item_name);
			sb.append("」を持つポケモン");
			return new String(sb);
		}
		@Override
		public void openDialog(Context context,
				SearchTypes search_type, SearchIfListener listener) {
			// 
			SearchIf.createSimpleSpinnerDialogBuilder(context,search_type,listener,this,ItemDataManager.INSTANCE.getAllItemName()).create().show();
		}
		@Override
		public PokeData[] search(PokeData[] poke_array, String category,
				String item_name) {
			// 
			if(toString().equals(category)){
				List<PokeData> list=new ArrayList<PokeData>();
				for(PokeData poke:poke_array){
					if(poke.hasItem(ItemDataManager.INSTANCE.getItemData(item_name)))
						list.add(poke);
				}
				return list.toArray(new PokeData[0]);
			}
			return new PokeData[0];
		}

		/**
		 * フリーワードから検索条件(_case)を返す
		 * 該当文字列)
		 */
		@Override
		public String getCaseByFreeWord(String free_word) {
			// TODO Auto-generated method stub
			return "";
		}
	},
	KETAGURI_KUSAMUSUBI("草結びの威力"){
		@Override
		public void openDialog(Context context, SearchTypes search_type,
				SearchIfListener listener) {
			// 
			SearchIf.createIntInputDialogBuilder(context, search_type, listener, this, 20, 120).create().show();
		}

		@Override
		public PokeData[] search(PokeData[] poke_array, String category,
				String _case) {
			// 
			if(toString().equals(category)){
				String[] pow_option=_case.split(" ");
				int pow=Integer.valueOf(pow_option[0]);
				OneCompareOptions option=OneCompareOptions.fromString(pow_option[1]);
				
				List<PokeData> list=new ArrayList<PokeData>();
				for(PokeData poke:poke_array){
					int tmp=120;
					final int weight=poke.getWeight();
					if(weight<=100)		  tmp=20;
					else if(weight<=250)  tmp=40;
					else if(weight<=500)  tmp=60;
					else if(weight<=1000) tmp=80;
					else if(weight<=2000) tmp=100;
					if(option.compareOf(tmp, pow)){
						list.add(poke);
					}
				}
				return list.toArray(new PokeData[0]);
			}
			return new PokeData[0];
		}

		/**
		 * フリーワードから検索条件(_case)を返す
		 * 該当文字列)
		 */
		@Override
		public String getCaseByFreeWord(String free_word) {
			// TODO Auto-generated method stub
			return "";
		}
	},
	FINAL_EX("最終経験値"){
		@Override
		public void openDialog(Context context,
				SearchTypes search_type, SearchIfListener listener) {
			// 
			SearchIf.createSimpleSpinnerDialogBuilder(context,search_type,listener,this,PokeData.FinalExperiences.toStringArray()).create().show();
		}
		@Override
		public PokeData[] search(PokeData[] poke_array, String category,
				String final_ex) {
			// 
			if(toString().equals(category)){
				List<PokeData> list=new ArrayList<PokeData>();
				for(PokeData poke:poke_array){
					if(poke.getFinalEx().toString().equals(final_ex))
						list.add(poke);
				}
				return list.toArray(new PokeData[0]);
			}
			return new PokeData[0];
		}

		/**
		 * フリーワードから検索条件(_case)を返す
		 * 該当文字列)
		 */
		@Override
		public String getCaseByFreeWord(String free_word) {
			// TODO Auto-generated method stub
			return "";
		}
	},
	KIND("種類"){
		@Override
		public boolean isCategory(String category) {
			// 
			for(KindCategories kind:KindCategories.values()){
				if(kind.toString().equals(category)){
					return true;
				}
			}
			return false;
		}

		@Override
		public void openDialog(Context context,
				SearchTypes search_type,SearchIfListener listener) {
			// 
			KindCategories.EVOLUTION.openDialog(context,search_type, listener);
		}

		@Override
		public PokeData[] search(PokeData[] poke_array, String category,
				String kind) {
			// 
			return KindCategories.fromString(category).search(poke_array,category,kind);
		}

		/**
		 * フリーワードから検索条件(_case)を返す
		 * 該当文字列)
		 */
		@Override
		public String getCaseByFreeWord(String free_word) {
			// TODO Auto-generated method stub
			return "";
		}
	};
	
	
	private static final String TAG="SeachableInformations for poke";
	
	/**
	 * search_if=category:case(SearchTypes)
	 * @param poke_array　現在のポケモンリスト
	 * @param condtion　検索条件
	 * @return　検索後のポケモンリスト
	 */
	public static PokeData[] searchBySearchIf(PokeData[] poke_array,String search_if){
		Utility.log(TAG, "searchBySearchIf");
		String[] tmp=search_if.split("[:¥(¥)/]");//category , case(SearchTypes)に分けられる
		Utility.log(TAG, tmp.length+":"+Arrays.deepToString(tmp));
		PokeData[] searched_array=null;
		Set<PokeData> searched_set=new HashSet<PokeData>();
		PokeSearchableInformations si=fromCategory(tmp[0]);
		if(si!=null){//検索であった場合
			switch(SearchTypes.fromString(tmp[2])){
				case FILTER:
					searched_array=si.search(poke_array,tmp[0], tmp[1]);
					searched_set.addAll(Arrays.asList(searched_array));
					break;
				case REMOVE:
					searched_array=si.search(poke_array,tmp[0], tmp[1]);
					for(PokeData poke:poke_array){
						boolean flag=true;
						for(PokeData target:searched_array){
							if(poke.equals(target)){ 
								flag=false;
								break;
							}
						}
						if(flag)
							searched_set.add(poke);
					}
					break;
				case ADD:
					searched_array=si.search(PokeDataManager.INSTANCE.getAllData(),tmp[0], tmp[1]);
					searched_set.addAll(Arrays.asList(poke_array));
					searched_set.addAll(Arrays.asList(searched_array));
					break;
				default:
					searched_set.addAll(Arrays.asList(poke_array));
					break;
			}
		}
		else{//検索以外(除外、追加)
			SearchTypes st=SearchTypes.fromString(tmp[0]);
			searched_set.addAll(Arrays.asList(poke_array));
			switch(st){
				case REMOVE:
					for(int i=1,n=tmp.length;i<n;i++){
						searched_set.remove(PokeDataManager.INSTANCE.getPokeData(tmp[i]));
					}
					break;
				case ADD:
					for(int i=1,n=tmp.length;i<n;i++){
						searched_set.add(PokeDataManager.INSTANCE.getPokeData(tmp[i]));
					}
					break;
				default:
			}
		}
		return searched_set.toArray(new PokeData[0]);
	}
	private final String name;
	private static final Map<String,PokeSearchableInformations>
		stringToEnum = new HashMap<String,PokeSearchableInformations>();//文字列からenumへ

	static { //定数名からenum定数へのマップを初期化
		for(PokeSearchableInformations si : values()){
			stringToEnum.put(si.toString(), si);
		}
	}
	/**
	 * 検索条件の種類から項目を取得
	 * @param category
	 * @return
	 */
	public static PokeSearchableInformations fromCategory(String category){
		for(PokeSearchableInformations info:values()){
			if(info.isCategory(category)) return info;
		}
		return null;
	}
	/**
	 * 文字列からSearchableInformationsを取得
	 * @param step
	 * @return
	 */
	public static PokeSearchableInformations fromString(String name){
		return stringToEnum.get(name);
	}

	
	/**
	 * 文字列配列で取得
	 * @return
	 */
	public static String[] toStringArray(){
		String[] array=new String[values().length];
		for(int i=0,n=array.length;i<n;i++){
			array[i]=values()[i].toString();
		}
		return array;
	}
	PokeSearchableInformations(String name){this.name=name;}

	
	/**
	 * ポケモンのページでタイプをクリックしたときなど
	 * デフォルトの検索条件を返す
	 * @param search_if
	 * @return
	 */
	public String getDefaultSearchIf(String _case){
		return SearchIf.createSearchIf(this,_case,SearchTypes.FILTER);
	}
	/**
	 * デフォルトの検索結果タイトルを返す
	 * @param _case
	 * @return
	 */
	public String getDefaultTitle(String _case){
		Utility.log(TAG, "getDefaultTitle");
		StringBuilder sb = new StringBuilder();
		sb.append("「");
		sb.append(_case);
		sb.append("」のポケモン");
		return new String(sb);
	}
	/**
	 * 検索条件文字列の種類が一致するかどうか
	 * @param category
	 * @return
	 */
	public boolean isCategory(String category){
		return toString().equals(category);
	}
	
	@Override
	public String toString(){return name;}

	/**
	 * フリーワードから検索条件(SearchIf)を取得
	 * @param free_word
	 * @return
	 */
	public static String[] getSearchIfByFreeWord(String free_word){
		List<String> search_ifs=new ArrayList<String>();
		
		final String PERTITION="[,¥(¥)¥/ ]";
		//free_wordをパーティションで配列に分割
		String[] words=free_word.split(PERTITION);
		for(String word:words){
			//語尾の"のポケモン","なポケモン","ポケモン"を取り除く
			for(PokeSearchableInformations si:values()){
				String _case=si.getCaseByFreeWord(word);
				if(!_case.equals("")){
					search_ifs.add(SearchIf.createSearchIf(si, _case, SearchTypes.FILTER));
				}
			}
		}
		
		return search_ifs.toArray(new String[0]);
	}



}

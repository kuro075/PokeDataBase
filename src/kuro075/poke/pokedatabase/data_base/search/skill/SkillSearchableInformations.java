package kuro075.poke.pokedatabase.data_base.search.skill;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import kuro075.poke.pokedatabase.R;
import kuro075.poke.pokedatabase.data_base.SearchIfListener;
import kuro075.poke.pokedatabase.data_base.SearchTypes;
import kuro075.poke.pokedatabase.data_base.poke.PokeData;
import kuro075.poke.pokedatabase.data_base.poke.PokeDataManager;
import kuro075.poke.pokedatabase.data_base.skill.SkillData;
import kuro075.poke.pokedatabase.data_base.search.OneCompareOptions;
import kuro075.poke.pokedatabase.data_base.search.SearchIf;
import kuro075.poke.pokedatabase.data_base.search.SearchIfCategory;
import kuro075.poke.pokedatabase.data_base.search.SkillSearchIfCategory;
import kuro075.poke.pokedatabase.data_base.search.poke.NameSearchOptions;
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
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

/**
 * SkillDataの検索可能情報
 * 名前(完了)、タイプ(完了)、威力(未完)、命中(未完？)、PP(完了)、優先度(完了)、分類(完了)、種類、対象(完了)、直接攻撃(完了?)
 *
 * @author sanogenma
 *
 */
public enum SkillSearchableInformations implements SkillSearchIfCategory{
	NAME("名前") {
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
			((TextView)layout.findViewById(R.id.text)).setText("※ひらがな、カタカナのみ有効");

			builder.setPositiveButton("検索",new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					String text=Utility.changeHiraToKata(edit.getText().toString());
					if(text.equals("")){
						Utility.popToast(context, "無効な文字列です");
						listener.receiveSearchIf(null);
					}else{
						StringBuilder sb=new StringBuilder();
						sb.append(edit.getText().toString());
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
		 * @param skill_array
		 * @param category
		 * @param _case : "ひらがな・カタカナの文字列 NameSearchOption"
		 * 
		 */
		@Override
		public SkillData[] search(SkillData[] skill_array, String category,
				String _case) {
			if(toString().equals(category)){
				String[] text_option=_case.split(" ");
				NameSearchOptions option=NameSearchOptions.fromString(text_option[1]);
				List<SkillData> skill_list=new ArrayList<SkillData>();
				for(SkillData chara:skill_array){
					//そのまま
					if(option.compareOf(chara, text_option[0])){
						skill_list.add(chara);
						continue;
					}
					//すべてひらがな
					if(option.compareOf(chara, Utility.changeKataToHira(text_option[0]))){
						skill_list.add(chara);
						continue;
					}
					//すべてカタカナ
					if(option.compareOf(chara, Utility.changeHiraToKata(text_option[0]))){
						skill_list.add(chara);
						continue;
					}
				}
				return skill_list.toArray(new SkillData[0]);
			}
			return new SkillData[0];
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
		public SkillData[] search(SkillData[] skill_array, String category,
				String type) {
			return TypeCategories.fromString(category).search(skill_array, category, type);
		}

		@Override
		public void openDialog(Context context, SearchTypes search_type,
				SearchIfListener listener) {
			TypeCategories.MYSELF.openDialog(context, search_type, listener);
		}

		@Override
		public String getCaseByFreeWord(String free_word) {
			// TODO Auto-generated method stub
			return null;
		}
		
	},
	POW("威力"){
		@Override
		public SkillData[] search(SkillData[] skill_array, String category,
				String _case) {
			String[] pow_option=_case.split(" ");
			int pow=Integer.valueOf(pow_option[0]);
			OneCompareOptions option=OneCompareOptions.fromString(pow_option[1]);
			
			List<SkillData> list=new ArrayList<SkillData>();
			for(SkillData skill:skill_array){
				if(skill.getPower()>0){
					if(option.compareOf(skill.getPower(),pow)){
						list.add(skill);
					}
				}
			}
			return list.toArray(new SkillData[0]);
		}

		@Override
		public void openDialog(Context context, SearchTypes search_type,
				SearchIfListener listener) {
			// TODO 威力変動するわざの考慮
			SearchIf.createIntInputDialogBuilder(context,search_type,listener,this,10,250).create().show();
		}

		@Override
		public String getCaseByFreeWord(String free_word) {
			// TODO Auto-generated method stub
			return null;
		}
	},
	HIT("命中率"){
		@Override
		public SkillData[] search(SkillData[] skill_array, String category,
				String _case) {
			String[] hit_option=_case.split(" ");
			int hit=Integer.valueOf(hit_option[0]);
			OneCompareOptions option=OneCompareOptions.fromString(hit_option[1]);
			
			List<SkillData> list=new ArrayList<SkillData>();
			for(SkillData skill:skill_array){
				int target_hit=skill.getHit();
				if(target_hit<=0){
					target_hit=100;
				}
				if(option.compareOf(target_hit,hit)){
					list.add(skill);
				}
			}
			return list.toArray(new SkillData[0]);
		}

		@Override
		public void openDialog(Context context, SearchTypes search_type,
				SearchIfListener listener) {
			SearchIf.createIntInputDialogBuilder(context,search_type,listener,this,30,100).create().show();
		}

		@Override
		public String getCaseByFreeWord(String free_word) {
			// TODO Auto-generated method stub
			return null;
		}
		
	},
	PP("PP"){
		@Override
		public SkillData[] search(SkillData[] skill_array, String category,
				String _case) {
			String[] pp_option=_case.split(" ");
			int pp=Integer.valueOf(pp_option[0]);
			OneCompareOptions option=OneCompareOptions.fromString(pp_option[1]);
			
			List<SkillData> list=new ArrayList<SkillData>();
			for(SkillData skill:skill_array){
				if(option.compareOf(skill.getPp(),pp)){
					list.add(skill);
				}
			}
			return list.toArray(new SkillData[0]);
		}

		@Override
		public void openDialog(Context context, SearchTypes search_type,
				SearchIfListener listener) {
			SearchIf.createIntInputDialogBuilder(context,search_type,listener,this,1,40).create().show();
		}

		@Override
		public String getCaseByFreeWord(String free_word) {
			// TODO Auto-generated method stub
			return null;
		}
	},
	PRIORITY("優先度"){
		@Override
		public SkillData[] search(SkillData[] skill_array, String category,
				String _case) {
			// TODO Auto-generated method stub
			String[] priority_option=_case.split(" ");
			int priority=Integer.valueOf(priority_option[0]);
			OneCompareOptions option=OneCompareOptions.fromString(priority_option[1]);
			
			List<SkillData> list=new ArrayList<SkillData>();
			for(SkillData skill:skill_array){
				if(option.compareOf(skill.getPriority(),priority)){
					list.add(skill);
				}
			}
			return list.toArray(new SkillData[0]);
		}

		@Override
		public void openDialog(Context context, SearchTypes search_type,
				SearchIfListener listener) {
			// TODO Auto-generated method stub
			SearchIf.createIntInputDialogBuilder(context,search_type,listener,this,-7,5).create().show();
		
		}

		@Override
		public String getCaseByFreeWord(String free_word) {
			// TODO Auto-generated method stub
			return null;
		}
	},
	SKILL_CLASS("分類"){
		@Override
		public SkillData[] search(SkillData[] skill_array, String category,
				String _case) {
			SkillClasses skill_class=SkillClasses.fromString(_case);
			List<SkillData> list=new ArrayList<SkillData>();
			for(SkillData skill:skill_array){
				if(skill.getSkillClass().equals(skill_class)){
					list.add(skill);
				}
			}
			
			return list.toArray(new SkillData[0]);
		}

		@Override
		public void openDialog(Context context, SearchTypes search_type,
				SearchIfListener listener) {
			// TODO Auto-generated method stub
			SearchIf.openSimpleListDialog(context,search_type,listener,this,Utility.changeToStringArray(SkillClasses.values()));
		}

		@Override
		public String getCaseByFreeWord(String free_word) {
			// TODO Auto-generated method stub
			return null;
		}
	},
	TARGET("攻撃対象"){

		@Override
		public SkillData[] search(SkillData[] skill_array, String category,
				String _case) {
			SkillData.AttackTargets target=SkillData.AttackTargets.fromString(_case);
			List<SkillData> list=new ArrayList<SkillData>();
			for(SkillData skill:skill_array){
				if(skill.getTarget().equals(target)){
					list.add(skill);
				}
			}
			
			return list.toArray(new SkillData[0]);
		}

		@Override
		public void openDialog(Context context, SearchTypes search_type,
				SearchIfListener listener) {
			SearchIf.openSimpleListDialog(context,search_type,listener,this,Utility.changeToStringArray(SkillData.AttackTargets.values()));
			
		}

		@Override
		public String getCaseByFreeWord(String free_word) {
			// TODO Auto-generated method stub
			return null;
		}
	},
	DIRECT("直接攻撃"){

		@Override
		public SkillData[] search(SkillData[] skill_array, String category,
				String _case) {
			SkillData.WhetherDirect direct=SkillData.WhetherDirect.fromString(_case);
			List<SkillData> list=new ArrayList<SkillData>();
			for(SkillData skill:skill_array){
				if(skill.getDirect().equals(direct)){
					list.add(skill);
				}
			}
			
			return list.toArray(new SkillData[0]);
		}

		@Override
		public void openDialog(Context context, SearchTypes search_type,
				SearchIfListener listener) {
			SearchIf.openSimpleListDialog(context,search_type,listener,this,Utility.changeToStringArray(SkillData.WhetherDirect.values()));
		}

		@Override
		public String getCaseByFreeWord(String free_word) {
			// TODO Auto-generated method stub
			return null;
		}
		
	},
	POKEMON("ポケモン"){
		public final String[] LEARNING_TYPE={"Lv","マ","卵","教"};
		final String[] KANA={"","あ","か","さ","た","な","は","ま","や","ら","わ"};
		public List<String> type_list=null;
		@Override
		public SkillData[] search(SkillData[] skill_array, String category,
				String _case) {
			if(toString().equals(category)){
				List<SkillData> list=new ArrayList<SkillData>();
				//skill_name lv マシン　タマゴ　教え　に分ける
				String[] poke_name_and_learning_type=_case.split(" ");
				PokeData poke=PokeDataManager.INSTANCE.getPokeData(poke_name_and_learning_type[0]);
				if(poke_name_and_learning_type.length==1){
					for(SkillData skill:skill_array){
						if(poke.hasSkill(skill)){
							list.add(skill);
						}
					}
				}else{
					String[] learning_types=poke_name_and_learning_type[1].split("・");
					//lv マシン タマゴ 教えの全ての場合
					if(learning_types.length==4){
						for(SkillData skill:skill_array){
							if(poke.hasSkill(skill)){
								list.add(skill);
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
						for(SkillData skill:skill_array){
							if(flag[0]){//レベル技
								if(poke.hasSkillByLvSkill(skill)){
									list.add(skill);
									continue;
								}
							}
							if(flag[1]){//マシン技
								if(poke.hasSkillByMachine(skill)){
									list.add(skill);
									continue;
								}
							}
							if(flag[2]){//タマゴ技
								if(poke.hasSkillByEggSkill(skill)){
									list.add(skill);
									continue;
								}
							}
							if(flag[3]){//教え技
								if(poke.hasSkillByTeachSkill(skill)){
									list.add(skill);
									continue;
								}
							}
						}
					}
				}
				return list.toArray(new SkillData[0]);
			}
			return new SkillData[0];
		}
		@Override
		public String getDefaultTitle(String _case){
			Utility.log(TAG, "getDefaultTitle");
			//skill_name lv マシン　タマゴ　教え　に分ける
			String[] skill_name_and_learning_type=_case.split(" ");
			StringBuilder sb = new StringBuilder();
			sb.append("「");
			sb.append(skill_name_and_learning_type[0]);
			sb.append("」が覚えるわざ");
			return new String(sb);
		}
		/**
		 * 技(skill),どうやって覚えるか(check),検索タイプ(search_type)
		 * @param skill
		 * @param check
		 * @param search_type
		 * @return
		 */
		private String getSearchIf(PokeData poke,boolean[] check,SearchTypes search_type){
			boolean flag=false;
			StringBuilder sb=new StringBuilder();
			sb.append(poke);
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
		public void openDialog(final Context context, final SearchTypes search_type,
				final SearchIfListener listener) {
			LayoutInflater factory=LayoutInflater.from(context);
			final View layout = factory.inflate(R.layout.dialog_skillfilter,null);
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setTitle(SearchIf.createDialogTitle(this, search_type));
			builder.setView(layout);
			
			ArrayAdapter<String> adapter;
			
			//頭文字スピナーの設定
			((TextView)layout.findViewById(R.id.text_skillclass)).setText("頭文字");
			final Spinner spinner_head = (Spinner)layout.findViewById(R.id.spinner_skillclass);
			adapter = new ArrayAdapter<String>(context,R.layout.center_spinner_item,KANA);
			spinner_head.setAdapter(adapter);

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
			//ポケモンのスピナーの設定
			((TextView)layout.findViewById(R.id.text_skill_name)).setText("ポケモン");
			final Spinner spinner_name = (Spinner)layout.findViewById(R.id.spinner_skillname);
			final List<String> poke_list=new ArrayList<String>();
			poke_list.addAll(Arrays.asList(PokeDataManager.INSTANCE.getAllName()));
			adapter = new ArrayAdapter<String>(context,R.layout.center_spinner_item,poke_list);
			spinner_name.setAdapter(adapter);
			
			final AdapterView.OnItemSelectedListener adapter_listener=new AdapterView.OnItemSelectedListener(){
				@Override
				public void onItemSelected(AdapterView<?> parent, View view, int position,long id) {
					List<String> list=new ArrayList<String>();
					//タイプと頭文字を考慮してポケモンリストを作成
					TypeData type=TypeData.fromString(type_list.get(spinner_type.getSelectedItemPosition()));
					int head_position=spinner_head.getSelectedItemPosition();
					for(PokeData poke:PokeDataManager.INSTANCE.getAllData()){
						if(type==null || poke.hasType(type)){//タイプが一致
							if(head_position==0){//頭文字が選択されていないとき
								list.add(poke.getName());
							}else{//頭文字が選択されているとき
								for(String head:Utility.getAiueoLine(head_position-1)){
									if(poke.isNameHead(head)){
										list.add(poke.getName());
										break;
									}
								}
							}
						}
					}
					for(int i=0,n=poke_list.size();i<n;i++) poke_list.remove(0);
					poke_list.addAll(list);
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,R.layout.center_spinner_item,poke_list.toArray(new String[0]));
					spinner_name.setAdapter(adapter);
					spinner_name.setEnabled(poke_list.size()>0);
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
			};
			
			
			//分類が変更された時の動作
			spinner_head.setOnItemSelectedListener(adapter_listener);
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
							listener.receiveSearchIf(getSearchIf(PokeDataManager.INSTANCE.getPokeData(poke_list.get(spinner_name.getSelectedItemPosition())),check_array,search_type));
						}else{
							Utility.popToast(context,"条件が不正です");
						}
					}else{
						Utility.popToast(context,"条件が不正です");
					}
				}
			});
			builder.setNegativeButton("戻る",new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// 
					listener.receiveSearchIf(null);
				}
			});
			((TableLayout)layout.findViewById(R.id.table_check)).setStretchAllColumns(true);
			builder.create().show();

			
		}

		@Override
		public String getCaseByFreeWord(String free_word) {
			// TODO Auto-generated method stub
			return null;
		}
	
	};
	
	
	
	
	
	public static SkillSearchableInformations fromCategory(String category){
		for(SkillSearchableInformations info:values()){
			if(info.isCategory(category)) return info;
		}
		return null;
	}

	
	
	/**
	 * フリーワード検索
	 * @param free_word
	 * @return
	 */
	public static String[] getSearchIfByFreeWord(String free_word){
		List<String> search_ifs=new ArrayList<String>();
		
		final String PERTITION="[,¥(¥)¥/ ]";
		//free_wordをパーティションで配列に分割
		String[] words=free_word.split(PERTITION);
		for(String word:words){
			//TODO 語尾の"のわざ","なわざ","技"を取り除く
			for(SkillSearchableInformations si:values()){
				String _case=si.getCaseByFreeWord(word);
				if(!_case.equals("")){
					search_ifs.add(SearchIf.createSearchIf(si, _case, SearchTypes.FILTER));
				}
			}
		}
		
		return search_ifs.toArray(new String[0]);
	}
	public static SkillData[] searchBySearchIf(SkillData[] skill_array,String search_if){
		Utility.log(TAG, "searchBySearchIf");
		String[] tmp=search_if.split("[:¥(¥)/]");//category , case(SearchTypes)に分けられる
		Utility.log(TAG, tmp.length+":"+Arrays.deepToString(tmp));
		SkillData[] searched_array=null;
		Set<SkillData> searched_set=new HashSet<SkillData>();
		SkillSearchableInformations si=fromCategory(tmp[0]);
		if(si!=null){//検索であった場合
			switch(SearchTypes.fromString(tmp[2])){
				case FILTER:
					searched_array=si.search(skill_array,tmp[0], tmp[1]);
					searched_set.addAll(Arrays.asList(searched_array));
					break;
				case REMOVE:
					searched_array=si.search(skill_array,tmp[0], tmp[1]);
					for(SkillData poke:skill_array){
						boolean flag=true;
						for(SkillData target:searched_array){
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
					searched_array=si.search(SkillDataManager.INSTANCE.getAllData(),tmp[0], tmp[1]);
					searched_set.addAll(Arrays.asList(skill_array));
					searched_set.addAll(Arrays.asList(searched_array));
					break;
				default:
					searched_set.addAll(Arrays.asList(skill_array));
					break;
			}
		}
		else{//検索以外(除外、追加)
			SearchTypes st=SearchTypes.fromString(tmp[0]);
			searched_set.addAll(Arrays.asList(skill_array));
			switch(st){
				case REMOVE:
					for(int i=1,n=tmp.length;i<n;i++){
						searched_set.remove(SkillDataManager.INSTANCE.getSkillData(tmp[i]));
					}
					break;
				case ADD:
					for(int i=1,n=tmp.length;i<n;i++){
						searched_set.add(SkillDataManager.INSTANCE.getSkillData(tmp[i]));
					}
					break;
				default:
			}
		}
		return searched_set.toArray(new SkillData[0]);
	}
	private final String name;


	private static final String TAG="SkillSearchableInformations";
	/**
	 * 文字列からSearchableInformationsを取得
	 * @param step
	 * @return
	 */
	public static SkillSearchableInformations fromString(String name){
		for(SkillSearchableInformations info:values()){
			if(info.toString().equals(name)){
				return info;
			}
		}
		return null;
	}
	
	
	SkillSearchableInformations(String name){this.name=name;}

	/**
	 * わざのページでタイプをクリックしたときなど
	 * デフォルトの検索条件を返す
	 * @param search_if
	 * @return
	 */
	public String getDefaultSearchIf(String _case){
		return SearchIf.createSearchIf(this, _case, SearchTypes.FILTER);
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
		sb.append("」のわざ");
		return new String(sb);
	}
	
	/**
	 * 検索条件文字列の種類が一致するかどうか
	 */
	public boolean isCategory(String category){
		return toString().equals(category);
	}

	@Override
	public String toString(){return name;}
	
}

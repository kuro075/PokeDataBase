package kuro075.poke.pokedatabase.data_base.search.character;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import kuro075.poke.pokedatabase.R;
import kuro075.poke.pokedatabase.data_base.SearchIfListener;
import kuro075.poke.pokedatabase.data_base.SearchTypes;
import kuro075.poke.pokedatabase.data_base.search.CharacterSearchIfCategory;
import kuro075.poke.pokedatabase.data_base.search.SearchIf;
import kuro075.poke.pokedatabase.data_base.search.poke.NameSearchOptions;
import kuro075.poke.pokedatabase.data_base.character.CharacterData;
import kuro075.poke.pokedatabase.data_base.character.CharacterDataManager;
import kuro075.poke.pokedatabase.util.Utility;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public enum CharacterSearchableInformations implements CharacterSearchIfCategory{
	NAME("名前") {
		private final String HIRA="あいうえおかきくけこさしすせそたちつてとなにぬねのはひふへほまみむめもやゆよらりるれろわをんがぎぐげござじずぜぞだぢづでどばびぶべぼぱぴぷぺぽぁぃぅぇぉゃゅょっ-";
		private final String KATA="アイウエオカキクケコサシスセソタチツテトナニヌネノハヒフヘホマミムメモヤユヨラリルレロワヲンガギグゲゴザジズゼゾダヂヅデドバビブベボパピプペポァィゥェォャュョッーｱｲｳｴｵｶｷｸｹｺｻｼｽｾｿﾀﾁﾂﾃﾄﾅﾆﾇﾈﾉﾊﾋﾌﾍﾎﾏﾐﾑﾒﾓﾔﾕﾖﾗﾘﾙﾚﾛﾜｦﾝｶﾞｷﾞｸﾞｹﾞｺﾞｻﾞｼﾞｽﾞｾﾞｿﾞﾀﾞﾁﾞﾂﾞﾃﾞﾄﾞﾊﾞﾋﾞﾌﾞﾍﾞﾎﾞﾊﾟﾋﾟﾌﾟﾍﾟﾎﾟｧｨｩｪｫｬｭｮｯ";
		
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
		/**
		 * 平仮名を含む文字列を全てカタカナの文字列に変換して返す
		 * 平仮名、カタカナ以外を含む場合は空の文字列("")を返す
		 * @param text
		 * @return
		 */
		public String changeHiraToKata(String text){
			StringBuilder sb=new StringBuilder();
			for(int i=0,n=text.length();i<n;i++){
				char c=text.charAt(i);
				//カタカナの場合
				if(KATA.indexOf(c)>=0){
					sb.append(c);
					continue;
				}
				
				int index=HIRA.indexOf(c);
				//平仮名の場合
				if(index>=0){
					sb.append(KATA.charAt(index));
					continue;
				}
				//それ以外
				return "";
			}
			return new String(sb);
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
					String text=changeHiraToKata(edit.getText().toString());
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
		 * @param character_array
		 * @param category
		 * @param _case : "カタカナの文字列 NameSearchOption"
		 * 
		 */
		@Override
		public CharacterData[] search(CharacterData[] character_array, String category,
				String _case) {
			if(toString().equals(category)){
				String[] text_option=_case.split(" ");
				NameSearchOptions option=NameSearchOptions.fromString(text_option[1]);
				List<CharacterData> character_list=new ArrayList<CharacterData>();
				for(CharacterData poke:character_array){
					if(option.compareOf(poke, text_option[0])){
						character_list.add(poke);
					}
				}
				return character_list.toArray(new CharacterData[0]);
			}
			return new CharacterData[0];
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
				String text=this.changeHiraToKata(new String(sb));
				sb=new StringBuilder();
				sb.append(text);
				sb.append(" ");
				sb.append(option);
				//_caseを返す
				return new String(sb);
			}
			return "";
		}
	};
	
	public static CharacterSearchableInformations fromCategory(String category){
		for(CharacterSearchableInformations info:values()){
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
		return null;
	}
	public static CharacterData[] searchBySearchIf(CharacterData[] chara_array,String search_if){
		Utility.log(TAG, "searchBySearchIf");
		String[] tmp=search_if.split("[:¥(¥)/]");//category , case(SearchTypes)に分けられる
		Utility.log(TAG, tmp.length+":"+Arrays.deepToString(tmp));
		CharacterData[] searched_array=null;
		Set<CharacterData> searched_set=new HashSet<CharacterData>();
		CharacterSearchableInformations si=fromCategory(tmp[0]);
		if(si!=null){//検索であった場合
			switch(SearchTypes.fromString(tmp[2])){
				case FILTER:
					searched_array=si.search(chara_array,tmp[0], tmp[1]);
					searched_set.addAll(Arrays.asList(searched_array));
					break;
				case REMOVE:
					searched_array=si.search(chara_array,tmp[0], tmp[1]);
					for(CharacterData poke:chara_array){
						boolean flag=true;
						for(CharacterData target:searched_array){
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
					searched_array=si.search(CharacterDataManager.INSTANCE.getAllData(),tmp[0], tmp[1]);
					searched_set.addAll(Arrays.asList(chara_array));
					searched_set.addAll(Arrays.asList(searched_array));
					break;
				default:
					searched_set.addAll(Arrays.asList(chara_array));
					break;
			}
		}
		else{//検索以外(除外、追加)
			SearchTypes st=SearchTypes.fromString(tmp[0]);
			searched_set.addAll(Arrays.asList(chara_array));
			switch(st){
				case REMOVE:
					for(int i=1,n=tmp.length;i<n;i++){
						searched_set.remove(CharacterDataManager.INSTANCE.getCharacterData(tmp[i]));
					}
					break;
				case ADD:
					for(int i=1,n=tmp.length;i<n;i++){
						searched_set.add(CharacterDataManager.INSTANCE.getCharacterData(tmp[i]));
					}
					break;
				default:
			}
		}
		return searched_set.toArray(new CharacterData[0]);
	}
	private final String name;


	private static final String TAG="CharacterSearchableInformations";
	/**
	 * 文字列からSearchableInformationsを取得
	 * @param step
	 * @return
	 */
	public static CharacterSearchableInformations fromString(String name){
		for(CharacterSearchableInformations info:values()){
			if(info.toString().equals(name)){
				return info;
			}
		}
		return null;
	}
	
	
	CharacterSearchableInformations(String name){this.name=name;}

	/**
	 * 特性のページでタイプをクリックしたときなど
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
		sb.append("」の特性");
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

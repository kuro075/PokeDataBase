package kuro075.poke.pokedatabase.poke_book;

import java.util.ArrayList;
import java.util.List;

import kuro075.poke.pokedatabase.R;
import kuro075.poke.pokedatabase.data_base.SearchTypes;
import kuro075.poke.pokedatabase.data_base.search.SearchIf;
import kuro075.poke.pokedatabase.data_base.search.poke.NameSearchOptions;
import kuro075.poke.pokedatabase.data_base.search.poke.PokeSearchableInformations;
import kuro075.poke.pokedatabase.util.Utility;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * あいうえお別検索アクティビティー
 * @author sanogenma
 *
 */
public class AiueoSearchActivity extends Activity{
	private static final String TAG="AiueoSearchActivity";
	private static final String has_dakuon="カキクケコサシスセソタチツテトハヒフヘホ",dakuon="ガギグゲゴザジズゼゾダヂヅデドバビブベボ";
	private static final String has_handakuon="ハヒフヘホ",handakuon="パピプペポ";
	
	Button[] name_buttons=new Button[44];
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aiueo_search_layout);
        Log.v("SelectPokename_buttonsActivity", "onCreate");
		
        
        //ア行
        name_buttons[0] = (Button) findViewById(R.id.button_a);
        name_buttons[1] = (Button) findViewById(R.id.button_i);
        name_buttons[2] = (Button) findViewById(R.id.button_u);
        name_buttons[3] = (Button) findViewById(R.id.button_e);
        name_buttons[4] = (Button) findViewById(R.id.button_o);
        //カ行
        name_buttons[5] = (Button) findViewById(R.id.button_ka);
        name_buttons[6] = (Button) findViewById(R.id.button_ki);
        name_buttons[7] = (Button) findViewById(R.id.button_ku);
        name_buttons[8] = (Button) findViewById(R.id.button_ke);
        name_buttons[9] = (Button) findViewById(R.id.button_ko);
        //サ行
        name_buttons[10] = (Button) findViewById(R.id.button_sa);
        name_buttons[11] = (Button) findViewById(R.id.button_si);
        name_buttons[12] = (Button) findViewById(R.id.button_su);
        name_buttons[13] = (Button) findViewById(R.id.button_se);
        name_buttons[14] = (Button) findViewById(R.id.button_so);
        //タ行
        name_buttons[15] = (Button) findViewById(R.id.button_ta);
        name_buttons[16] = (Button) findViewById(R.id.button_ti);
        name_buttons[17] = (Button) findViewById(R.id.button_tu);
        name_buttons[18] = (Button) findViewById(R.id.button_te);
        name_buttons[19] = (Button) findViewById(R.id.button_to);
        //ナ行
        name_buttons[20] = (Button) findViewById(R.id.button_na);
        name_buttons[21] = (Button) findViewById(R.id.button_ni);
        name_buttons[22] = (Button) findViewById(R.id.button_nu);
        name_buttons[23] = (Button) findViewById(R.id.button_ne);
        name_buttons[24] = (Button) findViewById(R.id.button_no);
        //ハ行
        name_buttons[25] = (Button) findViewById(R.id.button_ha);
        name_buttons[26] = (Button) findViewById(R.id.button_hi);
        name_buttons[27] = (Button) findViewById(R.id.button_hu);
        name_buttons[28] = (Button) findViewById(R.id.button_he);
        name_buttons[29] = (Button) findViewById(R.id.button_ho);
        //マ行
        name_buttons[30] = (Button) findViewById(R.id.button_ma);
        name_buttons[31] = (Button) findViewById(R.id.button_mi);
        name_buttons[32] = (Button) findViewById(R.id.button_mu);
        name_buttons[33] = (Button) findViewById(R.id.button_me);
        name_buttons[34] = (Button) findViewById(R.id.button_mo);
        //ヤ行
        name_buttons[35] = (Button) findViewById(R.id.button_ya);
        name_buttons[36] = (Button) findViewById(R.id.button_yu);
        name_buttons[37] = (Button) findViewById(R.id.button_yo);
        //ラ行
        name_buttons[38] = (Button) findViewById(R.id.button_ra);
        name_buttons[39] = (Button) findViewById(R.id.button_ri);
        name_buttons[40] = (Button) findViewById(R.id.button_ru);
        name_buttons[41] = (Button) findViewById(R.id.button_re);
        name_buttons[42] = (Button) findViewById(R.id.button_ro);
        //ワ行
        name_buttons[43] = (Button) findViewById(R.id.button_wa);
        
        //クリックリスナー
        for(final Button button:name_buttons){
        	button.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					pushButtonName(button.getText().toString());
				}
			});
        }
	}

	/**
	 * ボタンを押した時の動作
	 * @param name
	 */
	private void pushButtonName(String head){
		List<String> if_list=new ArrayList<String>();
		if_list.add(PokeSearchableInformations.NAME.getDefaultSearchIf(head));
		//濁音かどうか
		int index=has_dakuon.indexOf(head);
		if(index>=0){
			StringBuilder sb=new StringBuilder();
			sb.append(dakuon.charAt(index));
			sb.append(" ");
			sb.append(NameSearchOptions.START);
			if_list.add(SearchIf.createSearchIf(PokeSearchableInformations.NAME, new String(sb), SearchTypes.ADD));
		}
		//半濁音かどうか
		index=has_handakuon.indexOf(head);
		if(index>=0){
			StringBuilder sb=new StringBuilder();
			sb.append(handakuon.charAt(index));
			sb.append(" ");
			sb.append(NameSearchOptions.START);
			if_list.add(SearchIf.createSearchIf(PokeSearchableInformations.NAME, new String(sb), SearchTypes.ADD));
		}
		PokeSearchResultActivity.startThisActivity(this, PokeSearchableInformations.NAME.getDefaultTitle(head), if_list.toArray(new String[0]));
	}
	
	/**
	 * このアクティビティーを開始する
	 * @param context
	 */
	public static void startThisActivity(Context context){
		Utility.log(TAG, "startThisActivity");
		Intent intent = new Intent(context,AiueoSearchActivity.class);
		context.startActivity(intent);
	}
}

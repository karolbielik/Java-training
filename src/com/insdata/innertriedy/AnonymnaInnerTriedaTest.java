package com.insdata.innertriedy;

/**
 * Created by karol.bielik on 14. 3. 2017.
 */
public class AnonymnaInnerTriedaTest {

    public static void main(String[] args) {
        Okno okno = new Okno();
        okno.setButton(new Button());

        //anonymna trieda ktora pretazuje metodu onButtonClick
        Okno.ButtonClickedListenerImpl buttomClick = okno.new ButtonClickedListenerImpl(){
            private int clickCount = 0;
            @Override
            public void onButtonClick() {
                clickCount += 2;
                System.out.println("ja si tu budem robit co chcem, klikam po dvoch, clickCount:"+clickCount);
            }
            public void jaSomNovaMetoda(){
                System.out.println("nerobim nic");
            }
        };
        //Kompilacna chyba => pretoze Trieda ButtonClickedListenerImpl nema metodu jaSomNovaMetoda()
//        buttomClick.jaSomNovaMetoda();

        okno.getButton().addButtonClicked(buttomClick);
        okno.getButton().clickButton();
        okno.getButton().clickButton();
        okno.getButton().clickButton();
        okno.getButton().clickButton();

        System.out.println("---------------------------------------------------------------------------------");

        //implementacia anonymnej triedy pomocou implementacie interface
        //jedine miesto kedy mozeme vidiet kluc. slovo new pred Interfeisom
        okno.getButton().addButtonClicked(new ButtonClickedListener(){
            private int clickCount = 0;
            @Override
            public void onButtonClick() {
                clickCount += 3;
                System.out.println("ja si tu budem robit co chcem, klikam po troch, clickCount:"+clickCount);
            }
        });
        okno.getButton().clickButton();
        okno.getButton().clickButton();
        okno.getButton().clickButton();
        okno.getButton().clickButton();

    }
}


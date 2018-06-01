package com.insdata.innertriedy;

/**
 * Created by karol.bielik on 14. 3. 2017.
 */
//pouzitie inner tried je pre pripady kedy potrebujeme od triedy oddelit
    //nejaku funkcionality(spravanie) triedy ale zaroven toto potrebujeme
    //aby ostalo sucastou triedy, pretoze potrebujeme pristupovat k atributom
    //vonkajsej triedy
    //Typickym prikladom je kod ktory handluje eventy

public class InnerTriedaTest {

    public static void main(String[] args) {
        Okno okno = new Okno();
        okno.setButton(new Button());
        //instanciu inner klasy mozem spravit len cez instanciu outer klasy
        okno.getButton().addButtonClicked(okno.new ButtonClickedListenerImpl());
        okno.getButton().clickButton();
        okno.getButton().clickButton();
        okno.getButton().clickButton();
        okno.getButton().clickButton();
    }
}

class Okno{
    private Button button;

    //inner trieda je trieda ktora je definovana vo vnutri inej triedy
    //rovnako ako atribut alebo metoda
    //Moze pristupovat aj k instancnym aj k statickym zlenom vonkajsej triedy
    //oficialne sa vola non-static nested class alebo inner class.
    class ButtonClickedListenerImpl implements ButtonClickedListener{
        private int clickCount;
        @Override
        public void onButtonClick() {
            clickCount++;
            button.setStatus(!button.isStatus());
            System.out.println("button clicked "+clickCount+" times, having status "+button.isStatus());
            //ak by som potreboval poslat niekam instanciu outer clasy
            OknoLogger.logButtonStatus(Okno.this);
        }
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }
}


class Button{
    private boolean status;
    ButtonClickedListener buttonClickedListener;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean showButtonStatus(){
        return status;
    }

    public void clickButton(){
        this.buttonClickedListener.onButtonClick();
    }

    public void addButtonClicked(ButtonClickedListener buttonClickedListener){
        this.buttonClickedListener = buttonClickedListener;
    }
}

interface ButtonClickedListener{
    void onButtonClick();
}

class OknoLogger {
    public static void logButtonStatus(Okno okno){
        System.out.println("log button status:"+okno.getButton().isStatus());
    }
}
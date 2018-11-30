package com.youme.candid.youmeapp.Activity.model;

public class ZodiacSign {

       private boolean isSelected;
        private String ZodiacSign;
        int position;



    public ZodiacSign(String zodiacSign, int position) {
        ZodiacSign = zodiacSign;
        this.position = position;
    }

    public ZodiacSign() {
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getZodiacSign() {
        return ZodiacSign;
    }

    public void setZodiacSign(String zodiacSign) {
        ZodiacSign = zodiacSign;
    }

    public boolean getSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }


}

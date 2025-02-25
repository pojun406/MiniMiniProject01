class UserAPI {
    void mLine(char vC01, int su){
        for(int i = 0 ; i<= su ; i++){
            System.out.print(vC01);
        }
        System.out.println("");

    }

    String mLineReturn(char vChar, int vInt){
        String Ch01= "";
        for(int i =0 ; i<= vInt; i++){
            Ch01 += vChar;
        }

        return Ch01;
    }



    void add(int num){
        for(int i=2; i<=9 ;i++ ){
            System.out.print(num + " * " + i + " = " + (num * i));
            System.out.println("");
        }

    }
}
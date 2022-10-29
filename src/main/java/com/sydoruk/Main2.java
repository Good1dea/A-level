package com.sydoruk;

 class Main2 {

    public static void Triangle(){
        int sideA = (int) (Math.random() * 11);
        int sideB = (int) (Math.random() * 11);
        int sideC = (int) (Math.random() * 11);
        if((sideA + sideB > sideC) && (sideB + sideC > sideA) && (sideA + sideC > sideB)){
            double p = (sideA + sideB + sideC) / 2.0;
            double area = Math.sqrt((p * (p - sideA) * (p - sideB) * (p - sideC)));
            System.out.println("Площа трикутника зі сторонами " + sideA + ", " + sideB + " і " + sideC +
                    " дорівнює " + area);
        } else {
            System.out.println("Фігура зі сторонами " + sideA + ", " + sideB + " і " + sideC + " не є трикутником");
        }
    }

    public static void Minimal(){
        double random1 = Math.random() * 200 - 100;
        double random2 = Math.random() * 200 - 100;
        double random3 = Math.random() * 200 - 100;
        double abs1 = Math.abs(random1);
        double abs2 = Math.abs(random2);
        double abs3 = Math.abs(random3);
        double min = (abs1 < abs2 && abs1 < abs3) ? abs1 : (abs2 < abs3 ? abs2 : abs3);
        System.out.println("Випадкові числа: " + random1 + ", " + random2 + ", " + random3 +". " +
                "З них найменше за модулем " + min);
    }

    public static void Even(){
        int number = (int) (Math.random() * 100 + 1);
        String numberIsEven = number % 2 == 0 ? "є парним" : "є непарним";
        System.out.println("Число " + number + " " + numberIsEven);
    }

    public static void Converter() {
        int random = (int) (Math.random() * 100);
        System.out.println("Число у десятковій системі: " + random);
        StringBuilder binary = new StringBuilder();
        while (random > 0) {
            binary.append(random % 2);
            random = random / 2;
        }
        binary.reverse();
        System.out.println("Число у двійковій системі: " + binary);
    }

    public static void main(String[] args){
        System.out.println("Завдання 1");
        Triangle();
        System.out.println("Завдання 2");
        Minimal();
        System.out.println("Завдання 3");
        Even();
        System.out.println("Додаткове завдання");
        Converter();
    }
}


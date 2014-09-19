package sot.test;

public class Callbacks {

    public static void main(String[] args) {
        printingAlgorithm(new PrintingCallback() {
            public void printBody() {
                System.out.println("custom body");
            }

            public void printHeader() {
                System.out.println("---------");
            }

            public void printFooter() {
                System.out.println("+++++++++");
            }
        });
    }

    private static void printingAlgorithm(PrintingCallback callback) {
        callback.printHeader();
        callback.printBody();
        callback.printFooter();
    }
}


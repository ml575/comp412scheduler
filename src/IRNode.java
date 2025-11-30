public class IRNode {
    public int lineNum;
    public int opcode;
    public int[] args;
    public IRNode next;
    public IRNode prev;

    public IRNode(int lineNum, int opcode, int arg1, int arg2, int arg3) {
        this.lineNum = lineNum;
        this.opcode = opcode;
        this.args = new int[12];
        for (int i = 0; i < 12; i++) {
            this.args[i] = -1;
        }
        this.args[0] = arg1;
        this.args[4] = arg2;
        this.args[8] = arg3;
    }

}
package interfaces;


/**
 * Created by joe on 18/09/16.
 */
public interface ICommutiveOperator {
    ICommutiveOperator swapLhsRhs();
    boolean hasOperator(char operator);
    ICommutiveOperator zig();
}
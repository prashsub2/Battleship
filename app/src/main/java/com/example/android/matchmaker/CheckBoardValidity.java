package com.example.android.matchmaker;

/**
 * Created by prash on 5/2/2017.
 */

public class CheckBoardValidity {

    /**
     * If the number of blue boxes is equal to nine, then this method checks whether the blue boxes are in a valid
     * formation (i.e. whether there is a four box ship, three box ship, and a two box ship).
     *
     * @param booleanBoard - board that contains booleans corresponding to the boxes that are left blue.
     * @return - whether the ships have been placed in a valid formation or not.
     */
    public static boolean checkBoardFormation(boolean[][] booleanBoard) {

        boolean[][] tempBooleanBoard = new boolean[booleanBoard.length][booleanBoard[0].length];
        for (int i = 0; i < booleanBoard.length; i++) {
            for (int j = 0; j < booleanBoard[i].length; j++) {
                tempBooleanBoard[i][j] = booleanBoard[i][j];
            }
        }

        boolean fourBoxShip = false;
        boolean threeBoxShip = false;
        boolean twoBoxShip = false;

        for (int i = 0; i < tempBooleanBoard.length; i++) {
            for (int j = 0; j < tempBooleanBoard[i].length; j++) {
                if (tempBooleanBoard[i][j]) {
                    if (!fourBoxShip) {
                        fourBoxShip = checkFourBoxShip(i, j, tempBooleanBoard);
                    }

                    if (!threeBoxShip) {
                        threeBoxShip = checkThreeBoxShip(i, j, tempBooleanBoard);
                    }

                    if (!twoBoxShip) {
                        twoBoxShip = checkTwoBoxShip(i, j, tempBooleanBoard);
                    }
                }
            }
        }

        boolean isValidBoard = false;
        if (fourBoxShip && threeBoxShip && twoBoxShip) {
            isValidBoard = true;
        }
        return isValidBoard;
    }

    /**
     * Checks whether player one has placed a four box ship.
     *
     * @param rowIndex         - Row index of 2D boolean array.
     * @param columnIndex      - Column index of 2D boolean array.
     * @param tempBooleanBoard - boolean 2D array. Its elements are true where there are boxes placed and false
     *                         otherwise.
     * @return - boolean determining whether the player placed a four box ship.
     */
    public static boolean checkFourBoxShip(int rowIndex, int columnIndex, boolean tempBooleanBoard[][]) {
        boolean fourBoxShipHorizontal, fourBoxShipVertical;

        fourBoxShipHorizontal = checkFourShipsHorizontal(rowIndex, columnIndex, tempBooleanBoard);
        fourBoxShipVertical = checkFourShipsVertical(rowIndex, columnIndex, tempBooleanBoard);

        return fourBoxShipHorizontal || fourBoxShipVertical;
    }


    /**
     * Checks whether player one has placed a three box ship.
     *
     * @param rowIndex         - Row index of 2D boolean array.
     * @param columnIndex      - Column index of 2D boolean array.
     * @param tempBooleanBoard - boolean 2D array. Its elements are true where there are boxes placed and false
     *                         otherwise.
     * @return - boolean determining whether the player placed a three box ship.
     */
    public static boolean checkThreeBoxShip(int rowIndex, int columnIndex, boolean tempBooleanBoard[][]) {
        boolean threeBoxShipHorizontal, threeBoxShipVertical;

        threeBoxShipHorizontal = checkThreeBoxShipHorizontal(rowIndex, columnIndex, tempBooleanBoard);
        threeBoxShipVertical = checkThreeBoxShipVertical(rowIndex, columnIndex, tempBooleanBoard);

        return threeBoxShipHorizontal || threeBoxShipVertical;
    }

    /**
     * Checks whether player one has placed a two box ship.
     *
     * @param rowIndex         - Row index of 2D boolean array.
     * @param columnIndex      - Column index of 2D boolean array.
     * @param tempBooleanBoard - boolean 2D array. Its elements are true where there are boxes placed and false
     *                         otherwise.
     * @return - boolean determining whether the player placed a two box ship.
     */
    public static boolean checkTwoBoxShip(int rowIndex, int columnIndex, boolean tempBooleanBoard[][]) {
        boolean twoBoxShipHorizontal, twoBoxShipVertical;

        twoBoxShipHorizontal = checkTwoBoxShipHorizontal(rowIndex, columnIndex, tempBooleanBoard);
        twoBoxShipVertical = checkTwoBoxShipVertical(rowIndex, columnIndex, tempBooleanBoard);

        return twoBoxShipHorizontal || twoBoxShipVertical;

    }

    /**
     * Method checks if there is a horizontal four box ship in tempBooleanBoard. If a box contains part of a ship
     * it will translate to being true in tempBooleanBoard.
     *
     * @param rowIndex         - Row index in tempBooleanBoard.
     * @param columnIndex      - Column index in tempBooleanBoard.
     * @param tempBooleanBoard - 2D array of booleans.
     * @return - whether or not tempBooleanBoard contains a horizontal four box ship.
     */
    public static boolean checkFourShipsHorizontal(int rowIndex, int columnIndex, boolean[][] tempBooleanBoard) {
        boolean fourBoxShip = false;
        if (columnIndex <= 1) {
            boolean fourShipsHorizontal = tempBooleanBoard[rowIndex][columnIndex] &&
                    tempBooleanBoard[rowIndex][columnIndex + 1] &&
                    tempBooleanBoard[rowIndex][columnIndex + 2] &&
                    tempBooleanBoard[rowIndex][columnIndex + 3];
            if (columnIndex == 0) {
                boolean lastValueIsFalse = !tempBooleanBoard[rowIndex][4];
                if (fourShipsHorizontal && lastValueIsFalse) {
                    for (int l = columnIndex + 3; l > columnIndex; l--) {
                        tempBooleanBoard[rowIndex][l] = false;
                    }
                    fourBoxShip = true;
                }
            }
            if (fourShipsHorizontal) {
                for (int l = columnIndex + 3; l > columnIndex; l--) {
                    tempBooleanBoard[rowIndex][l] = false;
                }
                fourBoxShip = true;
            }
        }
        return fourBoxShip;
    }

    /**
     * Method checks if there is a vertical four box ship in tempBooleanBoard. If a box contains part of a ship
     * it will translate to being true in tempBooleanBoard.
     *
     * @param rowIndex         - Row index in tempBooleanBoard.
     * @param columnIndex      - Column index in tempBooleanBoard.
     * @param tempBooleanBoard - 2D array of booleans.
     * @return - whether or not tempBooleanBoard contains a vertical four box ship.
     */
    public static boolean checkFourShipsVertical(int rowIndex, int columnIndex, boolean[][] tempBooleanBoard) {
        boolean fourBoxShip = false;
        if (rowIndex <= 1) {
            boolean fourShipsVertical = tempBooleanBoard[rowIndex][columnIndex] &&
                    tempBooleanBoard[rowIndex + 1][columnIndex] &&
                    tempBooleanBoard[rowIndex + 2][columnIndex] &&
                    tempBooleanBoard[rowIndex + 3][columnIndex];
            if (rowIndex == 0) {
                boolean lastValueIsFalse = !tempBooleanBoard[4][columnIndex];
                if (fourShipsVertical && lastValueIsFalse) {
                    for (int l = rowIndex + 3; l > rowIndex; l--) {
                        tempBooleanBoard[l][columnIndex] = false;
                    }
                    fourBoxShip = true;
                }
            }
            if (fourShipsVertical) {
                for (int l = rowIndex + 3; l > rowIndex; l--) {
                    tempBooleanBoard[l][columnIndex] = false;
                }
                fourBoxShip = true;
            }
        }
        return fourBoxShip;
    }

    /**
     * Method checks if there is a horizontal three box ship in tempBooleanBoard. If a box contains part of a ship
     * it will translate to being true in tempBooleanBoard.
     *
     * @param rowIndex         - Row index in tempBooleanBoard.
     * @param columnIndex      - Column index in tempBooleanBoard.
     * @param tempBooleanBoard - 2D array of booleans.
     * @return - whether or not tempBooleanBoard contains a horizontal three box ship.
     */
    public static boolean checkThreeBoxShipHorizontal(int rowIndex, int columnIndex, boolean[][] tempBooleanBoard) {
        boolean threeBoxShip = false;
        if (columnIndex <= 2) {
            boolean threeShipsHorizontal = tempBooleanBoard[rowIndex][columnIndex] &&
                    tempBooleanBoard[rowIndex][columnIndex + 1] &&
                    tempBooleanBoard[rowIndex][columnIndex + 2];
            if (columnIndex == 0) {
                boolean lastTwoValuesAreFalse = !tempBooleanBoard[rowIndex][4] && !tempBooleanBoard[rowIndex][3];
                if (threeShipsHorizontal && lastTwoValuesAreFalse) {
                    for (int l = columnIndex + 2; l > columnIndex; l--) {
                        tempBooleanBoard[rowIndex][l] = false;
                    }
                    threeBoxShip = true;
                }
            }
            if (columnIndex == 1) {
                boolean lastValueIsFalse = !tempBooleanBoard[rowIndex][4];
                if (threeShipsHorizontal && lastValueIsFalse) {
                    for (int l = columnIndex + 2; l > columnIndex; l--) {
                        tempBooleanBoard[rowIndex][l] = false;
                    }
                    threeBoxShip = true;
                }
            }
            if (threeShipsHorizontal) {
                for (int l = columnIndex + 2; l > columnIndex; l--) {
                    tempBooleanBoard[rowIndex][l] = false;
                }
                threeBoxShip = true;
            }
        }
        return threeBoxShip;
    }

    /**
     * Method checks if there is a vertical three box ship in tempBooleanBoard. If a box contains part of a ship
     * it will translate to being true in tempBooleanBoard.
     *
     * @param rowIndex         - Row index in tempBooleanBoard.
     * @param columnIndex      - Column index in tempBooleanBoard.
     * @param tempBooleanBoard - 2D array of booleans.
     * @return - whether or not tempBooleanBoard contains a vertical three box ship.
     */
    public static boolean checkThreeBoxShipVertical(int rowIndex, int columnIndex, boolean[][] tempBooleanBoard) {
        boolean threeBoxShip = false;
        if (rowIndex <= 2) {
            boolean threeShipsVertical = tempBooleanBoard[rowIndex][columnIndex] &&
                    tempBooleanBoard[rowIndex + 1][columnIndex] &&
                    tempBooleanBoard[rowIndex + 2][columnIndex];
            if (rowIndex == 0) {
                boolean lastTwoValuesAreFalse = !tempBooleanBoard[4][columnIndex] && !tempBooleanBoard[3][columnIndex];
                if (threeShipsVertical && lastTwoValuesAreFalse) {
                    for (int l = rowIndex + 2; l > rowIndex; l--) {
                        tempBooleanBoard[l][columnIndex] = false;
                    }
                    threeBoxShip = true;
                }
            }
            if (rowIndex == 1) {
                boolean lastValueIsFalse = !tempBooleanBoard[4][columnIndex];
                if (threeShipsVertical && lastValueIsFalse) {
                    for (int l = rowIndex + 2; l > rowIndex; l--) {
                        tempBooleanBoard[l][columnIndex] = false;
                    }
                    threeBoxShip = true;
                }
            }
            if (threeShipsVertical) {
                for (int l = rowIndex + 2; l > rowIndex; l--) {
                    tempBooleanBoard[l][columnIndex] = false;
                }
                threeBoxShip = true;
            }
        }
        return threeBoxShip;
    }

    /**
     * Method checks if there is a horizontal two box ship in tempBooleanBoard. If a box contains part of a ship
     * it will translate to being true in tempBooleanBoard.
     *
     * @param rowIndex         - Row index in tempBooleanBoard.
     * @param columnIndex      - Column index in tempBooleanBoard.
     * @param tempBooleanBoard - 2D array of booleans.
     * @return - whether or not tempBooleanBoard contains a horizontal two box ship.
     */
    public static boolean checkTwoBoxShipHorizontal(int rowIndex, int columnIndex, boolean[][] tempBooleanBoard) {
        boolean twoBoxShip = false;
        if (columnIndex <= 3) {
            boolean twoShipsHorizontal = tempBooleanBoard[rowIndex][columnIndex] &&
                    tempBooleanBoard[rowIndex][columnIndex + 1];
            if (columnIndex == 0) {
                boolean lastThreeValuesAreFalse = !tempBooleanBoard[rowIndex][4] && !tempBooleanBoard[rowIndex][3] &&
                        !tempBooleanBoard[rowIndex][2];
                if (twoShipsHorizontal && lastThreeValuesAreFalse) {
                    for (int l = columnIndex + 1; l > columnIndex; l--) {
                        tempBooleanBoard[rowIndex][l] = false;
                    }
                    twoBoxShip = true;
                }
            }
            if (columnIndex == 1) {
                boolean lastTwoValuesAreFalse = !tempBooleanBoard[rowIndex][4] && !tempBooleanBoard[rowIndex][3];
                if (twoShipsHorizontal && lastTwoValuesAreFalse) {
                    for (int l = columnIndex + 1; l > columnIndex; l--) {
                        tempBooleanBoard[rowIndex][l] = false;
                    }
                    twoBoxShip = true;
                }
            }
            if (columnIndex == 2) {
                boolean lastValueIsFalse = !tempBooleanBoard[rowIndex][4];
                if (twoShipsHorizontal && lastValueIsFalse) {
                    for (int l = columnIndex + 1; l > columnIndex; l--) {
                        tempBooleanBoard[rowIndex][l] = false;
                    }
                    twoBoxShip = true;
                }
            }
            if (twoShipsHorizontal) {
                for (int l = columnIndex + 1; l > columnIndex; l--) {
                    tempBooleanBoard[rowIndex][l] = false;
                }
                twoBoxShip = true;
            }
        }
        return twoBoxShip;
    }

    /**
     * Method checks if there is a vertical two box ship in tempBooleanBoard. If a box contains part of a ship
     * it will translate to being true in tempBooleanBoard.
     *
     * @param rowIndex         - Row index in tempBooleanBoard.
     * @param columnIndex      - Column index in tempBooleanBoard.
     * @param tempBooleanBoard - 2D array of booleans.
     * @return - whether or not tempBooleanBoard contains a vertical two box ship.
     */
    public static boolean checkTwoBoxShipVertical(int rowIndex, int columnIndex, boolean[][] tempBooleanBoard) {
        boolean twoBoxShip = false;
        if (rowIndex <= 3) {
            boolean twoShipsVertical = tempBooleanBoard[rowIndex][columnIndex] &&
                    tempBooleanBoard[rowIndex + 1][columnIndex];
            if (rowIndex == 0) {
                boolean lastThreeValuesAreFalse = !tempBooleanBoard[4][columnIndex] && !tempBooleanBoard[3][columnIndex] &&
                        !tempBooleanBoard[2][columnIndex];
                if (twoShipsVertical && lastThreeValuesAreFalse) {
                    for (int l = rowIndex + 1; l > rowIndex; l--) {
                        tempBooleanBoard[l][columnIndex] = false;
                    }
                    twoBoxShip = true;
                }
            }
            if (rowIndex == 1) {
                boolean lastTwoValuesAreFalse = !tempBooleanBoard[4][columnIndex] && !tempBooleanBoard[4][columnIndex];
                if (twoShipsVertical && lastTwoValuesAreFalse) {
                    for (int l = rowIndex + 1; l > rowIndex; l--) {
                        tempBooleanBoard[l][columnIndex] = false;
                    }
                    twoBoxShip = true;
                }
            }
            if (rowIndex == 2) {
                boolean lastValueIsFalse = !tempBooleanBoard[4][columnIndex];
                if (twoShipsVertical && lastValueIsFalse) {
                    for (int l = rowIndex + 1; l > rowIndex; l--) {
                        tempBooleanBoard[l][columnIndex] = false;
                    }
                    twoBoxShip = true;
                }
            }
            if (twoShipsVertical) {
                for (int l = rowIndex + 1; l > rowIndex; l--) {
                    tempBooleanBoard[l][columnIndex] = false;
                }
                twoBoxShip = true;
            }
        }
        return twoBoxShip;
    }
}

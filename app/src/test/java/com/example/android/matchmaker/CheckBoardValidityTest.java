package com.example.android.matchmaker;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by prash on 5/2/2017.
 * Test created to check methods that help users place their ships.
 */
public class CheckBoardValidityTest {
    boolean validBooleanBoardOne[][] = new boolean[][]{{true, false, true, false, true},
            {true, false, true, false, true},{true, false, true, false, true}, {true, false, false, false, false},
            {false, false, false, false, false}};

    boolean validBooleanBoardTwo[][] = new boolean[][]{{false, false, false, false, true},
            {false, true, false, false, true},{false, true, false, false, true}, {false, true, false, false, false},
            {false, true, false, true, true}};

    boolean invalidBooleanBoardOne[][] = new boolean[][]{{true, true, true, true, false},
            {false, false, false, false, false},{false, true, true, true, true}, {false, false, false, false, false},
            {true, false, false, false, false}};

    boolean invalidBooleanBoardTwo[][] = new boolean[][]{{false, true, true, true, false},
            {false, false, false, false, true},{false, true, true, false, true}, {false, false, false, false, true},
            {true, false, false, false, false}};

    /**
     * Tests whether or not the checkBoardFormation returns the right boolean when given a certain board.
     *
     * @throws Exception
     */
    @Test
    public void testCheckBoardFormation() throws Exception {
        assertTrue(CheckBoardValidity.checkBoardFormation(validBooleanBoardOne));
        assertTrue(CheckBoardValidity.checkBoardFormation(validBooleanBoardTwo));
        assertFalse(CheckBoardValidity.checkBoardFormation(invalidBooleanBoardOne));
        assertFalse(CheckBoardValidity.checkBoardFormation(invalidBooleanBoardTwo));
    }

    /**
     * Tests whether the method is correct in judging whether or not there is a four box ship at the index of the
     * board passed into the function.
     *
     * @throws Exception
     */
    @Test
    public void testCheckFourBoxShip() throws Exception {
        assertTrue(CheckBoardValidity.checkFourBoxShip(0,0,validBooleanBoardOne));
        assertTrue(CheckBoardValidity.checkFourBoxShip(0,0,invalidBooleanBoardOne));
        assertFalse(CheckBoardValidity.checkFourBoxShip(2,2,invalidBooleanBoardTwo));
        assertFalse(CheckBoardValidity.checkFourBoxShip(4,4,validBooleanBoardTwo));
    }

    /**
     * Tests whether the method is correct in judging whether or not there is a three box ship at the index of the
     * board passed into the function.
     *
     * @throws Exception
     */
    @Test
    public void testCheckThreeBoxShip() throws Exception {
        assertTrue(CheckBoardValidity.checkThreeBoxShip(0,2,validBooleanBoardOne));
        assertTrue(CheckBoardValidity.checkThreeBoxShip(1,4,invalidBooleanBoardTwo));
        assertFalse(CheckBoardValidity.checkThreeBoxShip(2,3,invalidBooleanBoardOne));
        assertFalse(CheckBoardValidity.checkThreeBoxShip(4,3,validBooleanBoardTwo));
    }

    /**
     * Tests whether the method is correct in judging whether or not there is a two box ship at the index of the
     * board passed into the function.
     *
     * @throws Exception
     */
    @Test
    public void testCheckTwoBoxShip() throws Exception {
        assertTrue(CheckBoardValidity.checkTwoBoxShip(0,4,validBooleanBoardOne));
        assertTrue(CheckBoardValidity.checkTwoBoxShip(4,3,validBooleanBoardTwo));
        assertFalse(CheckBoardValidity.checkTwoBoxShip(4,0,invalidBooleanBoardOne));
        assertFalse(CheckBoardValidity.checkTwoBoxShip(4,0,invalidBooleanBoardTwo));
    }

    /**
     * Tests that the method returns the right boolean when checking for a vertical four box ship at the given
     * location in the given boolean 2D array.
     */
    @Test
    public void testCheckFourBoxShipVertical() throws Exception{
        assertTrue(CheckBoardValidity.checkFourShipsVertical(0,0, validBooleanBoardOne));
        assertTrue(CheckBoardValidity.checkFourShipsVertical(1,1, validBooleanBoardTwo));
    }

    /**
     * Tests that the method returns the right boolean when checking for a horizontal four box ship at the given
     * location in the given boolean 2D array.
     */
    @Test
    public void testCheckFourBoxShipHorizontal() throws Exception{
        assertTrue(CheckBoardValidity.checkFourShipsHorizontal(0,0, invalidBooleanBoardOne));
        assertTrue(CheckBoardValidity.checkFourShipsHorizontal(2,1, invalidBooleanBoardOne));
    }

    /**
     * Tests that the method returns the right boolean when checking for a vertical three box ship at the given
     * location in the given boolean 2D array.
     */
    @Test
    public void testCheckThreeBoxShipVertical() throws Exception{
        assertTrue(CheckBoardValidity.checkThreeBoxShipVertical(0,2, validBooleanBoardOne));
        assertTrue(CheckBoardValidity.checkThreeBoxShipVertical(1,4, invalidBooleanBoardTwo));
        assertTrue(CheckBoardValidity.checkThreeBoxShipVertical(0,4, validBooleanBoardTwo));
    }

    /**
     * Tests that the method returns the right boolean when checking for a horizontal three box ship at the given
     * location in the given boolean 2D array.
     */
    @Test
    public void testCheckThreeBoxShipHorizontal() throws Exception{
        assertFalse(CheckBoardValidity.checkThreeBoxShipHorizontal(0,4, validBooleanBoardTwo));
        assertTrue(CheckBoardValidity.checkThreeBoxShipHorizontal(0,1, invalidBooleanBoardTwo));
    }

    /**
     * Tests that the method returns the right boolean when checking for a vertical two box ship at the given
     * location in the given boolean 2D array.
     */
    @Test
    public void testCheckTwoBoxShipVertical() throws Exception{
        assertTrue(CheckBoardValidity.checkTwoBoxShipVertical(0,4, validBooleanBoardOne));
        assertFalse(CheckBoardValidity.checkTwoBoxShipVertical(2,1, invalidBooleanBoardTwo));
    }

    /**
     * Tests that the method returns the right boolean when checking for a horizontal two box ship at the given
     * location in the given boolean 2D array.
     */
    @Test
    public void testCheckTwoBoxShipHorizontal() throws Exception{
        assertTrue(CheckBoardValidity.checkTwoBoxShipHorizontal(4,3, validBooleanBoardTwo));
        assertTrue(CheckBoardValidity.checkTwoBoxShipHorizontal(2,1, invalidBooleanBoardTwo));
    }
}
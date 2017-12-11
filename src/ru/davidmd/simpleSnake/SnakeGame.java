package ru.davidmd.simpleSnake;

import java.util.ArrayList;

public class SnakeGame {

	// ����� ������������ �������
	public class pos {
		int x;
		int y;
		
		//�����������
		pos(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	// ��������� �����������
	public static final int DIR_NORTH = 1;
	public static final int DIR_EAST = 2;
	public static final int DIR_SOUTH = 3;
	public static final int DIR_WEST = 4;
	
	
	// ������ � ������ �������� ����
	// ����������� ������ �� ��������� ������ ����� �������
	public static int mFieldX = 18;
	public static int mFieldY = 30;
	
	// ���� � ����
	public int mScore=0;

	// ������� - ������� ����
	private int mField[][] = new int[mFieldX][mFieldY];
	
	// ���� ���� - ������ ��������� ��������� ������� ��������
	private ArrayList<pos> mSnake = new ArrayList<pos>();

	// ������� ���������� �������� ����
	int mDirection = SnakeGame.DIR_EAST;
	
	// �������� �� �������� ������������ ������ ��
	// ������ ����� ��� ��� �������� ���� ��� ������� 
	int isGrowing = 0;

	
	//������ � �����
	int iApple = 2;
	int iOrange = 3;
	int iKiwi = 4;
	int iMine = -3;
	
	// ���������� �����������
	SnakeGame() {
		// ������� ������� ����
		for (int i = 0; i < mFieldX; i++)
			for (int j = 0; j < mFieldY; j++) {
				mField[i][j] = 0;
			}
		// ������� ����
		mSnake.add(new pos(2, 2));
		// ������ ������ ���� � ������� 
		// ��������� ���� - ���������� -1
		mField[2][2] = -1;
		mSnake.add(new pos(2, 3));
		mField[2][3] = -1;
		mSnake.add(new pos(2, 4));
		mField[2][4] = -1;
		 
		
		/////��������� ����
		for(int i=5; i<13;i++){
			mField[i][7] = -2;
			mField[i][25] = -2;
		}
		// ��������� �� ������� ���� �����
		addApple();
		addOrange(); 
		addKiwi();
		addMine();
		addMine();
	}

	// ����� ��������� ����� �� ������� ���� 
	// 
	// ��� �� ��������� ������ ��� ������ ��������� � ����...=_))
	//��� ��� 2, ������ 3, ���� 4
	
//	private void addApple() {
//		boolean par = false;
//		while (!par) {
//			int x = (int) (Math.random() * mFieldX);
//			int y = (int) (Math.random() * mFieldY);
//			if (mField[x][y] == 0) {
//				mField[x][y] = 2;
//				par = true;
//			}
//		}
//	}
//	private void addOrange() {
//		boolean par = false;
//		while (!par) {
//			int x = (int) (Math.random() * mFieldX);
//			int y = (int) (Math.random() * mFieldY);
//			if (mField[x][y] == 0) {
//				mField[x][y] = 3;
//				par = true;
//			}
//		}
//	}
//	private void addKiwi() {
//		boolean par = false;
//		while (!par) {
//			int x = (int) (Math.random() * mFieldX);
//			int y = (int) (Math.random() * mFieldY);
//			if (mField[x][y] == 0) {
//				mField[x][y] = 4;
//				par = true;
//			}
//		}
//	}
//	//���������� ���� � �����.  ��� ����� -3
//	private void addMine() {
//		boolean par = false;
//		while (!par) {
//			int x = (int) (Math.random() * mFieldX);
//			int y = (int) (Math.random() * mFieldY);
//			if (mField[x][y] == 0) {
//				mField[x][y] = -3;
//				par = true;
//			}
//		}
//	}
	
	void addApple(){
		add_el(iApple);
	}
	void addOrange(){
		add_el(iOrange);
	} 
	void addKiwi(){
		add_el(iKiwi);
	}
	void addMine(){
		add_el(iMine);
	}
	
	void add_el(int el){
		boolean par = false;
		while (!par) {
			int x = (int) (Math.random() * mFieldX);
			int y = (int) (Math.random() * mFieldY);
			if (mField[x][y] == 0){
				mField[x][y] = el;
				par = true;
			}
		}
	}


	// ���� ����� �������� � ���� ��� ������ ����
	// ����� ���������� ��� �������� ������� ������ �����������
	// ��� ������ ����������� ����
	// ��� ����, ����������� ������� ����������� � 
	// �����������, ����� �� ���� ������ � ��������� �����������
	// ���������� ��� ������� ������ ��������� � ���� ������
	public boolean nextMove() {
		// �������, ���� � ��� ���������� ���� ������
		switch (this.mDirection) {
		// ���� �� �����
		case DIR_NORTH: {
			// ����� ������������ ���������� � ������� �������
			// ������ ���� �� ���������� ����
			int nextX = mSnake.get(mSnake.size() - 1).x;
			int nextY = mSnake.get(mSnake.size() - 1).y - 1;
			// ���� �� �� ��������� � ������� ������
			// � ���� ������ ���� �� ���� ����� (� ��� ��� �������
			// ������� �������� � ��������� ������ ����)
			if ((nextY >= 0) && mField[nextX][nextY] == 0) {
				// �� �� ���������, ������ �� � ������ ������ ����
				if (isGrowing > 0) {
					// ���� ������, ��������� ����� ����� � �� 
					// ������� ����� ����
					isGrowing--;
				} else {
					// ���� �� ������, �� ����������� ����� ����
					mField[mSnake.get(0).x][mSnake.get(0).y] = 0;
					mSnake.remove(0);
				}
				//����� ���������� ������ ����
				mSnake.add(new pos(nextX, nextY));
				mField[nextX][nextY] = -1;
				// � �� ���� ��� ����������� :) ���������� ������
				return true;
			} else if ((nextY >= 0) && mField[nextX][nextY] == 1) {
				// ���� �� ��������� � ����������� ���������� ����
				return false;
			} else if ((nextY >= 0) && mField[nextX][nextY] > 1) {
				// � ��� ���� �� ��������� �� �����, 
				// ����� ����������� ����� ����� 
				isGrowing = isGrowing + 2;
				// ��������� �����!
				mScore=mScore+10;
				if(mScore==150){ //��� ��������� 15 ����� +1 ����� �� ����
					addMine();
				}
				if(mScore==300){ //��� ��������� 30 ����� +1 ����� �� ����
					addMine();
				}
				// � ��������� ������ ����
				// �� ��������������� ������ ����
				mField[nextX][nextY] = 0;
				mSnake.add(new pos(nextX, nextY));
				mField[nextX][nextY] = -1;
				// �� � �������������� ��������� �� ���� ����� �����!
				addOrange();
				return true;
			} else {
				// �� ���� ��������� ������� ���������� false
				return false;
			}
		}
		// ����� ��� �� �� �����, ������ 
		// ��� ������ �����������
		case DIR_EAST: {
			int nextX = mSnake.get(mSnake.size() - 1).x + 1;
			int nextY = mSnake.get(mSnake.size() - 1).y;
			if ((nextX < mFieldX) && mField[nextX][nextY] == 0) {
				if (isGrowing > 0) {
					isGrowing--;
				} else {
					mField[mSnake.get(0).x][mSnake.get(0).y] = 0;
					mSnake.remove(0);
				}
				mSnake.add(new pos(nextX, nextY));
				mField[nextX][nextY] = -1;
				return true;
			} else if ((nextX < mFieldX) && mField[nextX][nextY] == 1) {
				return false;
			} else if ((nextX < mFieldX) && mField[nextX][nextY] > 1) {
				isGrowing = isGrowing + 2;
				mScore=mScore+10;
				if(mScore==150){
					addMine();
				}
				if(mScore==300){
					addMine();
				}
				mField[nextX][nextY] = 0;
				mSnake.add(new pos(nextX, nextY));
				mField[nextX][nextY] = -1;
				addApple();
				return true;
			} else {
				return false;
			}
		}
		case DIR_SOUTH: {
			int nextX = mSnake.get(mSnake.size() - 1).x;
			int nextY = mSnake.get(mSnake.size() - 1).y + 1;
			if ((nextX < mFieldX) && mField[nextX][nextY] == 0) {
				if (isGrowing > 0) {
					isGrowing--;
				} else {
					mField[mSnake.get(0).x][mSnake.get(0).y] = 0;
					mSnake.remove(0);
				}
				mSnake.add(new pos(nextX, nextY));
				mField[nextX][nextY] = -1;
				return true;
			} else if ((nextX < mFieldX) && mField[nextX][nextY] == 1) {
				return false;
			} else if ((nextX < mFieldX) && mField[nextX][nextY] > 1) {
				isGrowing = isGrowing + 2;
				mScore=mScore+10;
				if(mScore==150){
					addMine();
				}
				if(mScore==300){
					addMine();
				}
				mField[nextX][nextY] = 0;
				mSnake.add(new pos(nextX, nextY));
				mField[nextX][nextY] = -1;
				addKiwi();
				return true;
			} else {
				return false;
			}
		}
		case DIR_WEST: {
			int nextX = mSnake.get(mSnake.size() - 1).x - 1;
			int nextY = mSnake.get(mSnake.size() - 1).y;
			if ((nextX >= 0) && mField[nextX][nextY] == 0) {
				if (isGrowing > 0) {
					isGrowing--;
				} else {
					mField[mSnake.get(0).x][mSnake.get(0).y] = 0;
					mSnake.remove(0);
				}
				mSnake.add(new pos(nextX, nextY));
				mField[nextX][nextY] = -1;
				return true;
			} else if ((nextX >= 0) && mField[nextX][nextY] == 1) {
				return false;
			} else if ((nextX >= 0) && mField[nextX][nextY] > 1) {
				isGrowing = isGrowing + 2;
				mScore=mScore+10;
				if(mScore==150){
					addMine();
				}
				if(mScore==300){
					addMine();
				}
				mField[nextX][nextY] = 0;
				mSnake.add(new pos(nextX, nextY));
				mField[nextX][nextY] = -1;
				addOrange();
				return true;
			} else {
				return false;
			}
		}
		}
		return false;
	}

	// ����� � ���� ������ ������� � �������
	// ����� ��� � ��������� ������
	public int getDirection() {
		return mDirection;
	}
	
	public void clearScore(){
		this.mScore=0;
	}

	public void setDirection(int direction) {
		this.mDirection = direction;
	}

	public int[][] getmField() {
		return mField;
	}

	public int getSnakeLength() {
		return  mSnake.size();
	}

	public ArrayList<pos> getmSnake() {
		return mSnake;
	}
}

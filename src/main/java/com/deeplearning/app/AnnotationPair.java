package com.deeplearning.app;

public class AnnotationPair {
		
		private String left;
		private int right;

		public AnnotationPair(String left, int right) {
			this.left = left;
			this.right = right;
		}

		public String getLeft(){
			return this.left;
		}
		public int getRight(){
			return this.right;
		}
		public String toString(){
			return "(" + this.left + "," + this.right + ")";
		}
	}
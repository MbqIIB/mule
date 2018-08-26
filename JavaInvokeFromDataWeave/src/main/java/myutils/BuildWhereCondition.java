/**
 * 
 */
package myutils;

/**
 * @author sanjeev
 *
 */
public class BuildWhereCondition {

	/**
	 * 
	 * 
	 */
	private String whereCondition;
	
	public BuildWhereCondition() {		
	}
	
	public BuildWhereCondition(String title, String author, String pubYear) {
		StringBuilder whereCondition = new StringBuilder(" 1 = 1 ");
		boolean parameterFound = false;
		if (title != null && !title.isEmpty()) {
			parameterFound = true;
			whereCondition.append(" AND ( UPPER(title) = :title ");			
		} 
		if (author != null && !author.isEmpty()) {
			if (parameterFound) {
				whereCondition.append(" OR UPPER(author) = :author ");
			} else {
				whereCondition.append(" AND ( UPPER(author) = :author ");
			}
			parameterFound = true;
		} 
		if (pubYear != null && !pubYear.isEmpty()) {
			if (parameterFound) {
				whereCondition.append(" OR UPPER(pubYear) = :pubYear ");
			} else {
				whereCondition.append(" AND ( UPPER(pubYear) = :pubYear ");
			}
			parameterFound = true;
		}
		if (parameterFound) {
			whereCondition.append(")");
		}
		this.whereCondition = whereCondition.toString();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * @param title
	 * @param author
	 * @param pubYear
	 * @return whereCondition
	 */
	public static String buildBookWhereCondition(String title, String author, String pubYear) {
		System.out.println("-------------------->>>>>>>>>>>>title:" + title + " Author:" + author + " PubYear:" + pubYear);
		StringBuilder whereCondition = new StringBuilder(" 1 = 1 ");
		boolean parameterFound = false;
		if (title != null && !title.isEmpty()) {
			parameterFound = true;
			whereCondition.append(" AND ( UPPER(title) = :title ");			
		} 
		if (author != null && !author.isEmpty()) {
			if (parameterFound) {
				whereCondition.append(" OR UPPER(author) = :author ");
			} else {
				whereCondition.append(" AND ( UPPER(author) = :author ");
			}
			parameterFound = true;
		} 
		if (pubYear != null && !pubYear.isEmpty()) {
			if (parameterFound) {
				whereCondition.append(" OR UPPER(pubYear) = :pubYear ");
			} else {
				whereCondition.append(" AND ( UPPER(pubYear) = :pubYear ");
			}
			parameterFound = true;
		}
		if (parameterFound) {
			whereCondition.append(")");
		}
		return whereCondition.toString();

	}

}

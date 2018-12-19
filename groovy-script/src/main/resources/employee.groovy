import org.mule.runtime.core.api.util.CaseInsensitiveHashMap;
LinkedList<CaseInsensitiveHashMap<String, String>> rSet = new LinkedList<CaseInsensitiveHashMap<String, String>>();
CaseInsensitiveHashMap<String, String> rowDataMap = new CaseInsensitiveHashMap<String, String>();
rowDataMap.put("GENDER", "F");
rowDataMap.put("DOB", "1985-09-02T00:00:00");
rowDataMap.put("LAST_NAME", "PUCKETT");
rowDataMap.put("ID", 1011);
rowDataMap.put("FIRST_NAME", "CHAVA");
rowDataMap.put("HIRE_DATE", "2008-10-12T00:00:00");
rSet.add(rowDataMap);

rowDataMap = new CaseInsensitiveHashMap<String, String>();
rowDataMap.put("GENDER", "M");
rowDataMap.put("DOB", "1971-12-03T00:00:00");
rowDataMap.put("LAST_NAME", "TILLMAN");
rowDataMap.put("ID", 1012);
rowDataMap.put("FIRST_NAME", "CHRISTOPHER");
rowDataMap.put("HIRE_DATE", "2006-11-01T00:00:00");
rSet.add(rowDataMap);

return rSet;
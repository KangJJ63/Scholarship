package out.example.scholarinfo;

import android.net.Uri;
import android.util.Pair;

import java.io.Serializable;
import java.util.HashMap;

import out.example.scholarinfo.questions.IQuestionView;
import out.example.scholarinfo.questions.values.IValue;

public class Prize { // 장학금에 대한 정보를 저장한다
    private long id;
    private String name;
    private String startData;
    private String endData;
    private Uri imageRoute;
    private boolean isIn;
    private String appway; //신청방법
    private String amount; //지원금액
    private String doc;    //제출서류
    private String subplace; //제출장소
    private String pmethod;  //지급방법
    private String url;

    private HashMap<String, IValue> values = new HashMap<>();

    public Prize(long id, String name, String startData, String endData, boolean isIn,
                 String appway,String amount, String doc,String subplace,
                 String pmethod, String url) {
        this.startData = startData;
        this.endData = endData;
        this.id = id;
        this.isIn = isIn;
        this.name = name;
        this.appway = appway;
        this.amount= amount;
        this.doc=doc;
        this.subplace=subplace;
        this.pmethod=pmethod;
        this.url=url;
    }

    public IValue getValue(String name) {
        return values.get(name);
    }

    public String getAppway() {return appway; }

    public String getAmount() {return amount; }

    public String getDoc() { return doc; }

    public String getSubplace() { return subplace; }

    public String getPmethod() { return pmethod; }

    public String getUrl() { return url; }

    public String getName() {
        return name;
    }

    public boolean isIn() {
        return isIn;
    }

    public void setImageRoute(Uri imageRoute) {
        this.imageRoute = imageRoute;
    }

    public long getId() {
        return id;
    }

    public String getStartData() {
        return startData;
    }

    public String getEndData() {
        return endData;
    }

    public Uri getImageRoute() {
        return imageRoute;
    }

    public void addValue(String name, IValue value) {
        values.put(name,value);
    }

    public HashMap<String, IValue> getValues() {
        return values;
    }
}

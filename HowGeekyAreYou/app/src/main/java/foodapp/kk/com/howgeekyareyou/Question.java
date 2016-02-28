package foodapp.kk.com.howgeekyareyou;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.HashMap;

public class Question implements Parcelable {
    private String imageUrl;

    public HashMap<String,ArrayList<String>> hashMap= new  HashMap<String,ArrayList<String>>();
    private String scores="";
    public static Parcelable.Creator<Question> getCREATOR() {
        return CREATOR;
    }

    public Question(String questionData[]){
        ArrayList<String> answerList = new ArrayList<String>();

        if((questionData.length%2) ==0){
            for(int i=2;i<questionData.length;i++) {
                if(i%2==0)
                    answerList.add(questionData[i]);
                else
                    scores+=(questionData[i])+",";
            }
            hashMap.put(questionData[1], answerList);
        } else if((questionData.length%2)!=0){

            for(int i=2;i<questionData.length-1;i++) {
                if(i%2==0)
                    answerList.add(questionData[i]);
                else {
                    scores+=(questionData[i])+",";
                }
            }
            hashMap.put(questionData[1], answerList);
            imageUrl=questionData[questionData.length-1];
        }
    }
    @Override
    public int describeContents() {
        return 0;
    }

    public HashMap getHashMap(){
        return hashMap;
    }

    public String getImageUrl(){
        return imageUrl;
    }

    public ArrayList<String> getOptionsList(String key){
        return hashMap.get(key);
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeMap(hashMap);
        dest.writeString(imageUrl);
        dest.writeString(scores);
    }

    public static final Parcelable.Creator<Question> CREATOR
            = new Parcelable.Creator<Question>() {
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    private Question(Parcel in) {
        in.readMap(hashMap,String.class.getClassLoader());
        this.imageUrl=in.readString();
        this.scores=in.readString();
    }
    @Override
    public String toString() {

        return  hashMap.toString()+imageUrl;
    }
    public String[] getScores(){

        return scores.split(",");
    }

}
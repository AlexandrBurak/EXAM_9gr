//package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

class Employee{
    public Integer empl_id;
    public String empl_surname;
    public String empl_name;
    public String skill;
    public Integer wrk_sum = 0;

    public Employee(Scanner sc){
        this.empl_id = Integer.valueOf(sc.next());
        this.empl_surname = sc.next();
        this.empl_name = sc.next();
        this.skill = sc.next().split("$")[0];
    }

    @Override
    public String toString(){
        return empl_id + ";" + empl_surname + ";" + empl_name + ";" + skill;
    }
}

class Dates{
    public String date1;
    public String date2;
    public Date date1_d;
    public Date date2_d;

    public Dates(Scanner sc) throws ParseException {
        this.date1 = sc.next();
        this.date2 = sc.next().split("$")[0];
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
        this.date1_d = formatter.parse(date1);
        this.date2_d = formatter.parse(date2);

    }
}

class Project{
    public Integer project_id;
    public String project_title;

    public Project(Scanner sc){
        this.project_id = Integer.valueOf(sc.next());
        this.project_title = sc.next().split("$")[0];
    }

    @Override
    public String toString(){
        return project_id + ";" + project_title;
    }
}

class Request{
    public String skill;
    public Integer workload;
    public String res;

    public Request(Scanner sc){
        this.skill = sc.next();
        this.workload = Integer.valueOf(sc.next().split("$")[0]);
    }
}

class Position{
    public Integer position_id;
    public Integer project_id;
    public Integer empl_id;
    public Integer workload;
    public String billing_type;
    public Integer workload_sum = 0;

    public Position(Scanner sc){
        this.position_id = Integer.valueOf(sc.next());
        this.project_id = Integer.valueOf(sc.next());
        this.empl_id = Integer.valueOf(sc.next());
        this.workload = Integer.valueOf(sc.next());
        this.billing_type = sc.next().split("$")[0];
    }
    @Override
    public String toString(){
        return position_id + ";" + project_id + ";" + empl_id + ";" + workload + ";" + billing_type;
    }
}

class OpenPosition{
    public Integer project_id;
    public Integer position_id;
    public String open_date;
    public Date date;

    public OpenPosition(Scanner sc) throws ParseException {
        this.project_id = Integer.valueOf(sc.next());
        this.position_id = Integer.valueOf(sc.next());
        this.open_date = sc.next().split("$")[0];
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
        this.date = formatter.parse(open_date);
    }

    @Override
    public String toString(){
        return  project_id + ";" + position_id + ";" + open_date;
    }
}

public class Main {

    public static void main(String[] args) throws IOException, FileNotFoundException, ParseException {
        Scanner sc1 = new Scanner(new File("input1.txt"));
        sc1.useDelimiter("[;\\n]");
        ArrayList<Employee> emp = new ArrayList<>();
        while (sc1.hasNext()){
            emp.add(new Employee(sc1));
        }

        Scanner sc2 = new Scanner(new File("input2.txt"));
        sc2.useDelimiter("[;\\n]");
        ArrayList<Project> prj = new ArrayList<>();
        while (sc2.hasNext()){
            prj.add(new Project(sc2));
        }

        Scanner sc3 = new Scanner(new File("input3.txt"));
        sc3.useDelimiter("[;\\n]");
        ArrayList<Position> positions = new ArrayList<>();
        while (sc3.hasNext()){
            positions.add(new Position(sc3));
        }

        Scanner sc4 = new Scanner(new File("input4.txt"));
        ArrayList<OpenPosition> opns = new ArrayList<>();
        sc4.useDelimiter("[;\\n]");
        while(sc4.hasNext()){
            opns.add(new OpenPosition(sc4));
        }
        Scanner sc8 = new Scanner(new File("input8.txt"));
        Integer prj_id_in = sc8.nextInt();

        List<Position> pos_f = positions.stream().filter(x-> x.project_id.equals(prj_id_in)).collect(Collectors.toList());

        ArrayList<Employee> out2 = new ArrayList<>();
        for (int i = 0; i < pos_f.size(); i++) {
            int finalI = i;
            if (emp.stream().filter(x -> x.empl_id.equals(pos_f.get(finalI).empl_id)).collect(Collectors.toList()).size() != 0) {
                out2.add(emp.stream().filter(x -> x.empl_id.equals(pos_f.get(finalI).empl_id)).collect(Collectors.toList()).get(0));
            }
        }
        Comparator<Employee> cmp2 = new Comparator<Employee>() {
            @Override
            public int compare(Employee o1, Employee o2) {
                return o1.empl_id.compareTo(o2.empl_id);
            }
        };
        Collections.sort(out2, cmp2);
        PrintWriter pw2 = new PrintWriter(new File("output2.txt"));
        for(int i = 0; i < out2.size(); i++){
            if(i == out2.size() - 1){
                pw2.print(out2.get(i).empl_id + ";" + out2.get(i).empl_surname + ";" + out2.get(i).empl_name);
                break;
            }
            pw2.println(out2.get(i).empl_id + ";" + out2.get(i).empl_surname + ";" + out2.get(i).empl_name);
        }
        pw2.flush();


        for(int i = 0; i < emp.size(); i++){
            int finalI = i;
            List<Position> tmp = positions.stream().filter(x-> x.empl_id.equals(emp.get(finalI).empl_id)).collect(Collectors.toList());
            Integer sum = 0;
            for(int j = 0; j < tmp.size(); j++){
                sum += tmp.get(j).workload;
            }
            emp.get(i).wrk_sum = sum;
        }
        Comparator<Employee> cmp1 = new Comparator<Employee>() {
            @Override
            public int compare(Employee o1, Employee o2) {
                return -o1.wrk_sum.compareTo(o2.wrk_sum);
            }
        };

        List<Employee> out1 = emp.stream().filter(x-> x.wrk_sum > 100).collect(Collectors.toList());
        Collections.sort(out1, cmp1);
        PrintWriter pw1 = new PrintWriter(new File("output1.txt"));
        for (int i = 0; i < out1.size(); i++){
            if(i == out1.size() - 1){
                pw1.print(out1.get(i).empl_id + ";" + out1.get(i).empl_surname + ";" + out1.get(i).empl_name + ";" + out1.get(i).wrk_sum);
                break;
            }
            pw1.println(out1.get(i).empl_id + ";" + out1.get(i).empl_surname + ";" + out1.get(i).empl_name + ";" + out1.get(i).wrk_sum);
        }
        pw1.flush();

        Scanner sc6 = new Scanner(new File("input6.txt"));
        ArrayList<Dates> dt = new ArrayList<>();
        sc6.useDelimiter("[;\\n]");
        while(sc6.hasNext()){
            dt.add(new Dates(sc6));
        }
        Scanner sc7 = new Scanner(new File("input7.txt"));
        Integer pj_id_in = sc7.nextInt();

       List<OpenPosition> dt_tr = opns.stream().filter(x-> x.project_id.equals(pj_id_in) & check(x.date, dt.get(0).date1_d, dt.get(0).date2_d)).collect(Collectors.toList());

        List<Project> out3 = prj.stream().filter(x-> x.project_id.equals(pj_id_in)).collect(Collectors.toList());
        PrintWriter pw3 = new PrintWriter(new File("output3.txt"));
        pw3.print(out3.get(0).project_title + ";" + dt_tr.size());
        pw3.flush();

        Scanner sc5 = new Scanner(new File("input5.txt"));
        sc5.useDelimiter("[;\\n]");
        ArrayList<Request> reqs = new ArrayList<>();
        while(sc5.hasNext()){
            reqs.add(new Request(sc5));
        }
        ArrayList<Employee> workers_non = new ArrayList<>();
        for(int i = 0; i < reqs.size(); i++){
            String tmp = reqs.get(i).skill;
            List<Employee> tmp_lst = emp.stream().filter(x-> x.skill.equals(tmp)).collect(Collectors.toList());
            for(int j = 0; j < tmp_lst.size(); j++){
                workers_non.add(tmp_lst.get(j));
            }
        }

        for(int i = 0; i < reqs.size(); i++){
            String tmp = reqs.get(0).skill;
            for(int j = 0; j < workers_non.size(); j++){
                int finalI = j;
                List<Position> strm;
                strm = positions.stream().filter(x-> x.empl_id.equals(workers_non.get(finalI).empl_id) & workers_non.get(finalI).skill.equals(tmp) & x.billing_type.equals("non-billable")).collect(Collectors.toList());
                Integer sum = 0;
                for(int k = 0; k < strm.size(); k++){
                    sum += strm.get(k).workload;
                }
                if(sum >= reqs.get(0).workload){
                    reqs.get(0).res = "Yes";
                }
                else{
                    reqs.get(0).res = "No";
                }
            }
        }

        PrintWriter pw4 = new PrintWriter(new File("output4.txt"));
        for(int i = 0; i < reqs.size(); i++){
            if(i == reqs.size() - 1){
                if(reqs.get(i).res.equals("Yes")){
                    pw4.print("Yes");
                }
                else{
                    pw4.print(reqs.get(i).skill.toString() + ";" + reqs.get(i).workload);
                }
                break;
            }
            if(reqs.get(i).res.equals("Yes")){
                pw4.println("Yes");
            }
            else{
                pw4.println(reqs.get(i).skill + ";" + reqs.get(i).workload);
            }
            break;
        }
        pw4.flush();





    }


    public static Boolean check(Date date, Date date1_d, Date date2_d){
        if(date1_d.compareTo(date) == -1 & date.compareTo(date2_d) == -1){
            return Boolean.TRUE;
        }
        else if(date1_d.compareTo(date) == 0 & date.compareTo(date2_d) == -1){
            return Boolean.TRUE;
        }
        else if(date1_d.compareTo(date) == -1 & date.compareTo(date2_d) == 0){
            return Boolean.TRUE;
        }
        else {
            return Boolean.FALSE;
        }


    }
}

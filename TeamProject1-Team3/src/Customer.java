public class Customer {
    private String name;
    private String birth;
    private String phoneNum;

    public Customer(String name, String birth, String phoneNum) {
        super();
        this.name = name;
        this.birth = birth;
        this.phoneNum = phoneNum;
    }

    public String getName() {
        return name;
    }

    public String getBirth() {
        return birth;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public boolean equals(Object obj) { // 고객 비교
        if (!(obj instanceof Customer))
            return false;
        Customer customer = (Customer) obj;
        if (!customer.name.equals(name))
            return false;
        if (!customer.birth.equals(birth))
            return false;
        if (!customer.phoneNum.equals(phoneNum))
            return false;
        return true;
    }

}

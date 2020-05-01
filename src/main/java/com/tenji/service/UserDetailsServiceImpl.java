package com.tenji.service;
import com.tenji.AppConfig;
import com.tenji.entity.MUser;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    AppConfig appConfig;

    public boolean addUser(MUser muser) {
        boolean flag=false;
        try{
            //UserDao.addUser(User);
            flag=true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    public boolean updateUser(MUser muser) {
        boolean flag=false;
        try{
            //UserDao.updateUser(User);
            flag=true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    public boolean deleteUser(String userCD) {
        boolean flag=false;
        try{
            //UserDao.deleteUser(userCD);
            flag=true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    public UserDetails loadUserByUsername(String userCD) throws UsernameNotFoundException {
        try (Connection connection = appConfig.dataSource().getConnection()) {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT UserCD,Password,Level FROM m_User where Usercd='" + userCD + "'");

            MUser muser = new MUser();

            while (rs.next()) {
                muser.setusercd(rs.getString("Usercd"));
                muser.setpassword(rs.getString("Password"));
            }
            //権限のリスト
            //AdminやUserなどが存在するが、今回は利用しないのでUSERのみを仮で設定
            //権限を利用する場合は、DB上で権限テーブル、ユーザ権限テーブルを作成し管理が必要
            List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
            GrantedAuthority authority = new SimpleGrantedAuthority("USER");
            grantList.add(authority);

            //rawDataのパスワードは渡すことができないので、暗号化
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            //UserDetailsはインタフェースなのでUserクラスのコンストラクタで生成したユーザオブジェクトをキャスト
            UserDetails userDetails = (UserDetails)new User(muser.getusercd(),
                    encoder.encode(muser.getpassword()),grantList);

            return userDetails;
        } catch (Exception e) {
            throw new UsernameNotFoundException("UserCD:" + userCD + "was not found in the database");
        }
    }
}

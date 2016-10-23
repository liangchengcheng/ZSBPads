package com.lcc.entity;

import java.io.Serializable;

/**
 * Created by lcc on 16/5/8.
 */
public class Result implements Serializable {

    /**
     * status : 1
     * message : 登录成功
     * result : {"id":1,"phone":"18813149871","tk":"76fb134c676327aa826dadef154f5d8d","updated_at":"2016-05-15 11:42:27","created_at":"0016-04-20 11:51:39","activity":"1","userinfo":{"id":1,"phone":"18813149871","nickname":"xiaochengcheng","xb":"男","email":"1038127753@qq.com","created_at":"2016年05月07日12:53:37","jf":"10","qm":"人生自古谁不死啊","zy":"程序员","user_image":"http://h.hiphotos.baidu.com/image/h%3D200/sign=71cd4229be014a909e3e41bd99763971/472309f7905298221dd4c458d0ca7bcb0b46d442.jpg"}}
     */

    private int status;
    private String message;
    /**
     * id : 1
     * phone : 18813149871
     * tk : 76fb134c676327aa826dadef154f5d8d
     * updated_at : 2016-05-15 11:42:27
     * created_at : 0016-04-20 11:51:39
     * activity : 1
     * userinfo : {"id":1,"phone":"18813149871","nickname":"xiaochengcheng","xb":"男","email":"1038127753@qq.com","created_at":"2016年05月07日12:53:37","jf":"10","qm":"人生自古谁不死啊","zy":"程序员","user_image":"http://h.hiphotos.baidu.com/image/h%3D200/sign=71cd4229be014a909e3e41bd99763971/472309f7905298221dd4c458d0ca7bcb0b46d442.jpg"}
     */

    private ResultEntity result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResultEntity getResult() {
        return result;
    }

    public void setResult(ResultEntity result) {
        this.result = result;
    }

    public static class ResultEntity implements Serializable{
        private int id;
        private String phone;
        private String tk;
        private String updated_at;
        private String created_at;
        private String activity;
        /**
         * id : 1
         * phone : 18813149871
         * nickname : xiaochengcheng
         * xb : 男
         * email : 1038127753@qq.com
         * created_at : 2016年05月07日12:53:37
         * jf : 10
         * qm : 人生自古谁不死啊
         * zy : 程序员
         * user_image : http://h.hiphotos.baidu.com/image/h%3D200/sign=71cd4229be014a909e3e41bd99763971/472309f7905298221dd4c458d0ca7bcb0b46d442.jpg
         */

        private UserinfoEntity userinfo;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getTk() {
            return tk;
        }

        public void setTk(String tk) {
            this.tk = tk;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getActivity() {
            return activity;
        }

        public void setActivity(String activity) {
            this.activity = activity;
        }

        public UserinfoEntity getUserinfo() {
            return userinfo;
        }

        public void setUserinfo(UserinfoEntity userinfo) {
            this.userinfo = userinfo;
        }

        public static class UserinfoEntity {
            private int id;
            private String phone;
            private String nickname;
            private String xb;
            private String email;
            private String created_at;
            private String jf;
            private String qm;
            private String zy;
            private String user_image;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getXb() {
                return xb;
            }

            public void setXb(String xb) {
                this.xb = xb;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public String getJf() {
                return jf;
            }

            public void setJf(String jf) {
                this.jf = jf;
            }

            public String getQm() {
                return qm;
            }

            public void setQm(String qm) {
                this.qm = qm;
            }

            public String getZy() {
                return zy;
            }

            public void setZy(String zy) {
                this.zy = zy;
            }

            public String getUser_image() {
                return user_image;
            }

            public void setUser_image(String user_image) {
                this.user_image = user_image;
            }
        }
    }
}

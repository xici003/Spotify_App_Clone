import flask
import pyodbc
from flask_cors import CORS

#Khởi tạo ứng dụng web
app = flask.Flask(__name__)
CORS(app)

# str_connect = 'DRIVER={ODBC Driver 17 for SQL Server};SERVER=DESKTOP-DQLIPGF;DATABASE=music;Trusted_Connection=yes'
str_connect = 'DRIVER={ODBC Driver 17 for SQL Server};SERVER=DESKTOP-KV701FT;DATABASE=MusicApp;Trusted_Connection=yes'
connect = pyodbc.connect(str_connect)

#         ALBUM
@app.route("/album/getall", methods = ["GET"])
def getAllAlbum():
    try:
        cursor = connect.cursor()
        cursor.execute("Select * from Album")

        result = []
        key = []

        for i in cursor.description:
            key.append(i[0])
        for val in cursor.fetchall():
            result.append(dict(zip(key, val)))

        resp = flask.jsonify(result)
        resp.status_code

        return  resp
    except Exception as e:
        print(e)

@app.route("/album/getAlbumById/<id>",methods = ["GET"])
def getAlbumById(id):
    try:
        cursor = connect.cursor()
        cursor.execute("Select * from Album where ID_Album = ?",id)

        result = []
        key = []
        for i in cursor.description:
            key.append(i[0])
        for val in cursor.fetchall():
            result.append(dict(zip(key,val)))

        resp = flask.jsonify(result)
        resp.status_code = 200
        return resp
    except Exception as e:
        print(e)

@app.route("/album/getAlbumByName/<name>",methods = ["GET"])
def getAlbumByName(name):
    try:
        cursor = connect.cursor()
        cursor.execute("Select * from Album where Name LIKE ?",'%' + name + '%')

        result = []
        key = []
        for i in cursor.description:
            key.append(i[0])
        for val in cursor.fetchall():
            result.append(dict(zip(key,val)))

        resp = flask.jsonify(result)
        resp.status_code = 200
        return resp
    except Exception as e:
        print(e)

@app.route("/album/add", methods=['POST'])
def addAlbum():
    try:
        id = flask.request.json.get('ID_Album')
        name = flask.request.json.get('Name')
        description = flask.request.json.get('Description')
        thumbnail = flask.request.json.get('Thumbnail')

        data = (id,name,description, thumbnail)
        sql = 'insert into Album(ID_Album, Name, Description, Thumbnail) values(?,?,?,?)'

        cursor = connect.cursor()
        cursor.execute(sql, data)
        connect.commit()
        resp = flask.jsonify({'message': 'Thêm album thành công'})
        resp.status_code = 200
        return resp
    except Exception as e:
        print(e)
        resp = flask.jsonify({'error': 'Internal Server Error'})
        resp.status_code = 500
        return resp

@app.route('/album/delete/<id>', methods = ['DELETE'])
def deleteAlbum(id):
    try:
        sql = 'delete from Album where ID_Album = ?'
        cursor = connect.cursor()
        cursor.execute(sql,id)
        connect.commit()
        resp = flask.jsonify({'mess':'xoa thanh cong'})
        resp.status_code = 200
        return resp
    except Exception as e:
        print(e)

@app.route('/album/update/<id>', methods = ['PUT'])
def updateAlbum(id):
    try:
        name = flask.request.json.get('Name')
        description = flask.request.json.get('Description')
        thumbnail = flask.request.json.get('Thumbnail')

        data = ( name, description, thumbnail,id)
        sql = 'update Album set  Name =?, Description=?, Thumbnail=? where ID_Album =?'

        cursor = connect.cursor()
        cursor.execute(sql,data)
        connect.commit()
        resp = flask.jsonify({'mess': 'sửa thành công'})
        resp.status_code = 200
        return resp
    except Exception as e:
        print(e)

##############  SONG
@app.route("/song/getSongById/<id>",methods = ["GET"])
def getSongById(id):
    try:
        cursor = connect.cursor()
        cursor.execute("Select * from Song where ID_Song = ?",id)

        result = []
        key = []
        for i in cursor.description:
            key.append(i[0])
        for val in cursor.fetchall():
            result.append(dict(zip(key,val)))

        resp = flask.jsonify(result)
        resp.status_code = 200
        return resp
    except Exception as e:
        print(e)

@app.route("/song/getSongByName/<name>",methods = ["GET"])
def getSongByName(name):
    try:
        cursor = connect.cursor()
        cursor.execute("Select * from Song where Name LIKE ?",'%' + name + '%')

        result = []
        key = []
        for i in cursor.description:
            key.append(i[0])
        for val in cursor.fetchall():
            result.append(dict(zip(key,val)))

        resp = flask.jsonify(result)
        resp.status_code = 200
        return resp
    except Exception as e:
        print(e)

@app.route("/song/getall",methods = ["GET"])
def getAllSong():
    try:
        cursor = connect.cursor()
        cursor.execute("Select * from Song")
        result = []
        key = []

        for i in cursor.description:
            key.append(i[0])
        for val in cursor.fetchall():
            result.append(dict(zip(key,val)))

        resp = flask.jsonify(result)
        resp.status_code

        return resp

    except Exception as e:
        print(e)

@app.route("/song/getSongsbyAlbum/<id>", methods = ["GET"])
def getSongsByAlbum(id):
    try:
        cursor = connect.cursor()
        cursor.execute("Select Song.*, Artist.Name as nameArtist, Artist.Thumbnails from Song inner join Artist on "
                       "Song.ID_Artist = Artist.ID_Artist where ID_Album = ?",id)

        result = []
        key = []
        for i in cursor.description:
            key.append(i[0])
        for val in cursor.fetchall():
            result.append(dict(zip(key,val)))

        resp = flask.jsonify(result)
        resp.status_code = 200
        return resp
    except Exception as e:
        print(e)

@app.route("/song/add", methods=['POST'])
def addSong():
    try:
        id = flask.request.json.get('ID_Song')
        thumbnail = flask.request.json.get('Thumbnail')
        mp3 = flask.request.json.get('URLmp3')
        name = flask.request.json.get('Name')
        viewcount = flask.request.json.get('ViewCount')
        description = flask.request.json.get('Description')
        lyrics = flask.request.json.get('Lyrics')
        idAuthor = flask.request.json.get('ID_Author')
        idAlbum = flask.request.json.get('ID_Album')

        data = (id, thumbnail,mp3,name,viewcount,description,lyrics,idAlbum,idAuthor)
        sql = 'insert into Song(ID_Song, Thumbnail, URLmp3, Name, ViewCount, Description, Lyrics, ID_Album, ID_Author) values(?,?,?,?,?,?,?,?,?)'

        cursor = connect.cursor()
        cursor.execute(sql, data)
        connect.commit()
        resp = flask.jsonify({'message': 'Thêm song thành công'})
        resp.status_code = 200
        return resp
    except Exception as e:
        print(e)
        resp = flask.jsonify({'error': 'Internal Server Error'})
        resp.status_code = 500
        return resp

@app.route('/song/delete/<id>', methods = ['DELETE'])
def deleteSong(id):
    try:
        sql = 'delete from Song where ID_Song = ?'
        cursor = connect.cursor()
        cursor.execute(sql,id)
        connect.commit()
        resp = flask.jsonify({'mess':'xoa thanh cong'})
        resp.status_code = 200
        return resp
    except Exception as e:
        print(e)

@app.route('/song/update/<id>', methods = ['PUT'])
def updateSong(id):
    try:
        thumbnail = flask.request.json.get('Thumbnail')
        mp3 = flask.request.json.get('URLmp3')
        name = flask.request.json.get('Name')
        viewcount = flask.request.json.get('ViewCount')
        description = flask.request.json.get('Description')
        lyrics = flask.request.json.get('Lyrics')
        idAuthor = flask.request.json.get('ID_Author')
        idAlbum = flask.request.json.get('ID_Album')

        sql = 'UPDATE Song SET Thumbnail=?, URLmp3=?, Name=?, ViewCount=?, Description=?, Lyrics=?, ID_Album=?, ID_Author=? where ID_Song =?'
        data = ( thumbnail, mp3, name, viewcount, description, lyrics, idAlbum, idAuthor,id)

        cursor = connect.cursor()
        cursor.execute(sql,data)
        connect.commit()
        resp = flask.jsonify({'mess': 'sửa thành công'})
        resp.status_code = 200
        return resp
    except Exception as e:
        print(e)


#          PLAYLIST
@app.route("/playlist/getall", methods = ["GET"])
def getAllPlaylist():
    try:
        cursor = connect.cursor()
        cursor.execute("Select * from Playlist")

        result = []
        key = []

        for i in cursor.description:
            key.append(i[0])
        for val in cursor.fetchall():
            result.append(dict(zip(key, val)))

        resp = flask.jsonify(result)
        resp.status_code

        return  resp
    except Exception as e:
        print(e)

@app.route("/playlist/add", methods=['POST'])
def addPlaylist():
    try:
        id = flask.request.json.get('ID_Playlist')
        idacc = flask.request.json.get('ID_Acc')
        name = flask.request.json.get('Name')
        description = flask.request.json.get('Description')
        thumbnail = flask.request.json.get('Thumbnail')

        data = (id,idacc,name,description,thumbnail)
        sql = 'insert into Playlist(ID_PlayList, ID_Acc, Name, Description, Thumbnail) values(?,?,?,?,?)'

        cursor = connect.cursor()
        cursor.execute(sql, data)
        connect.commit()
        resp = flask.jsonify({'message': 'Thêm playlist thành công'})
        resp.status_code = 200
        return resp
    except Exception as e:
        print(e)
        resp = flask.jsonify({'error': 'Internal Server Error'})
        resp.status_code = 500
        return resp

@app.route('/playlist/delete/<id>', methods = ['DELETE'])
def deletePlaylist(id):
    try:
        sql = 'delete from Playlist where ID_Playlist = ?'
        cursor = connect.cursor()
        cursor.execute(sql,id)
        connect.commit()
        resp = flask.jsonify({'mess':'xoa thanh cong'})
        resp.status_code = 200
        return resp
    except Exception as e:
        print(e)

@app.route('/playlist/update/<id>', methods = ['PUT'])
def updatePlaylist(id):
    try:
        idacc = flask.request.json.get('ID_Acc')
        name = flask.request.json.get('Name')
        description = flask.request.json.get('Description')
        thumbnail = flask.request.json.get('Thumbnail')

        sql = 'update Playlist set ID_Acc=?, Name=?, Description=?, Thumbnail=? where ID_Playlist = ?'
        data = (idacc, name, description, thumbnail,id)
        cursor = connect.cursor()
        cursor.execute(sql,data)
        connect.commit()
        resp = flask.jsonify({'mess': 'sửa thành công'})
        resp.status_code = 200
        return resp
    except Exception as e:
        print(e)

# Account
@app.route("/account/getall", methods = ["GET"])
def getAllAccount():
    try:
        cursor = connect.cursor()
        cursor.execute("Select * from Account")

        result = []
        key = []

        for i in cursor.description:
            key.append(i[0])
        for val in cursor.fetchall():
            result.append(dict(zip(key, val)))

        resp = flask.jsonify(result)
        resp.status_code

        return  resp
    except Exception as e:
        print(e)

@app.route("/account/checkLogin/", methods=["POST"])
def checkLogin():
    try:
        # Lấy dữ liệu từ request
        data = flask.request.json
        email = data['Email']
        password = data['Password']

        # Truy vấn để tìm thông tin tài khoản theo email và mật khẩu
        cursor = connect.cursor()
        cursor.execute("SELECT * FROM Account WHERE Email = ? AND Password = ?", (email, password))

        account_info = cursor.fetchone()  # Lấy thông tin tài khoản

        if account_info:
            # Nếu thông tin đăng nhập hợp lệ, trả về thông tin tài khoản
            id, email, name, thumbnail, password = account_info
            response_data = {
                "message": "Đăng nhập thành công",
                "account": {
                    "ID_Acc": id,
                    "Email": email,
                    "Name": name,
                    "Thumbnail": thumbnail,
                    "Password": password
                }
            }
            resp = flask.jsonify(response_data)
            resp.status_code = 200  # Đặt mã trạng thái 200 (OK)
        else:
            # Thông tin đăng nhập không hợp lệ
            resp = flask.jsonify({"message": "Thông tin đăng nhập không đúng"})
            resp.status_code = 401  # Đặt mã trạng thái 401 (Unauthorized)

        return resp
    except Exception as e:
        # Xử lý ngoại lệ
        print("Lỗi:", e)
        resp = flask.jsonify({"message": "Đã xảy ra lỗi"})
        resp.status_code = 500  # Đặt mã trạng thái 500 (Lỗi máy chủ)
        return resp

@app.route("/account/add", methods=['POST'])
def addAccount():
    try:
        cursor = connect.cursor()
        email = flask.request.json.get('Email')
        name = flask.request.json.get('Name')
        thumbnail = flask.request.json.get('Thumbnail')
        password = flask.request.json.get('Password')

        # Kiểm tra xem email đã tồn tại chưa
        cursor.execute("SELECT * FROM Account WHERE Email = ?", (email))
        existing_account = cursor.fetchone()

        if existing_account:
            resp = flask.jsonify({'error': 'Email đã tồn tại'})
            resp.status_code = 409  # HTTP 409 Conflict
            return resp

        # Nếu không, thêm vào cơ sở dữ liệu
        sql = 'INSERT INTO Account(Email, Name, Password, Thumbnail) VALUES(?, ?, ?, ?)'
        data = (email, name, password, thumbnail)
        cursor.execute(sql, data)
        connect.commit()

        # Truy vấn lại tài khoản vừa được thêm
        cursor.execute("SELECT * FROM Account WHERE Email = ?", (email))
        new_account = cursor.fetchone()

        if new_account:
            account_info = {
                'ID_Acc': new_account[0],  # Giá trị của cột ID
                'Email': new_account[1],  # Giá trị của cột Email
                'Name': new_account[2],  # Giá trị của cột Name
                'Password': new_account[3],  # Giá trị của cột Password
                'Thumbnail': new_account[4],  # Giá trị của cột Thumbnail
            }

            resp = flask.jsonify({'message': 'Đăng ký thành công', 'account': account_info})
            resp.status_code = 201  # HTTP 201 Created
            return resp

        resp = flask.jsonify({'error': 'Không tìm thấy tài khoản vừa thêm'})
        resp.status_code = 404  # HTTP 404 Not Found
        return resp

    except Exception as e:
        print(e)
        resp = flask.jsonify({'error': 'Internal Server Error'})
        resp.status_code = 500
        return resp


@app.route('/account/delete/<id>', methods = ['DELETE'])
def deleteAccount(id):
    try:
        sql = 'delete from Account where ID_Acc = ?'
        cursor = connect.cursor()
        cursor.execute(sql,id)
        connect.commit()
        resp = flask.jsonify({'mess':'xoa account thanh cong'})
        resp.status_code = 200
        return resp
    except Exception as e:
        print(e)

@app.route('/account/update/<id>', methods = ['PUT'])
def updateAccount(id):
    try:
        email = flask.request.json.get('Email')
        name = flask.request.json.get('Name')
        thumbnail = flask.request.json.get('Thumbnail')

        sql = 'update Account set Email=?, Name=?, Thumbnail=? where ID_Acc = ?'
        data = (email, name, thumbnail, id)
        cursor = connect.cursor()
        cursor.execute(sql,data)
        connect.commit()
        resp = flask.jsonify({'mess': 'sửa account thành công'})
        resp.status_code = 200
        return resp
    except Exception as e:
        print(e)
if __name__ == "__main__":
    # CORS(app, origin='http://localhost:5215')
    # app.run(host='192.168.16.1', port=5000, debug='True')
    app.run(host='192.168.1.33', port=5000)
    # app.run(host='192.168.48.212', port=5000)
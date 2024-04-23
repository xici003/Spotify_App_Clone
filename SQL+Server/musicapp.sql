--create database MusicApp
use MusicApp
CREATE TABLE Account (
  ID_Acc int NOT NULL PRIMARY KEY,
  Email varchar(255) NOT NULL,
  Name nvarchar(255) NOT NULL,
  Thumbnail nvarchar(255) NULL,
  Password varchar(50) NOT NULL
);


--Alter TRIGGER insertAccount
--ON Account
--INSTEAD OF INSERT
--AS
--BEGIN
--    -- Tìm ID lớn nhất hiện tại trong bảng Account
--    DECLARE @max_id int;
--    SELECT @max_id = ISNULL(MAX(ID_Acc), 0) FROM Account;
    
--    -- Chèn dữ liệu mới với ID_Acc được tăng lên
--    INSERT INTO Account (ID_Acc, Email, Name, Thumbnail, Password)
--    SELECT @max_id + ROW_NUMBER() OVER(ORDER BY (SELECT NULL)), Email, Name, Thumbnail, Password
--    FROM inserted;
--END;

CREATE TABLE Album (
  ID_Album int NOT NULL PRIMARY KEY,
  Name nvarchar(255) NOT NULL,
  Description nvarchar(255) NULL,
  Thumbnail varchar(255) NULL
);
CREATE TABLE Artist (
  ID_Artist int NOT NULL PRIMARY KEY,
  Name nvarchar(255) NOT NULL,
  Thumbnails varchar(255) NULL,
);
CREATE TABLE Author (
  ID_Author int NOT NULL PRIMARY KEY,
  Name nvarchar(255) NOT NULL,
  Thumbnail varchar(255) NULL
);
CREATE TABLE Song (
  ID_Song int NOT NULL PRIMARY KEY,
  Thumbnail varchar(255) NULL,
  URLmp3 text NOT NULL,
  Name nvarchar(255) NOT NULL,
  ViewCount int NOT NULL,
  Description nvarchar(255) NULL,
  Lyrics nvarchar(2000) NULL,
  ID_Author int NULL,
  ID_Artist int NULL,
  ID_Album int NULL,
  FOREIGN KEY (ID_Author) REFERENCES Author(ID_Author),
  FOREIGN KEY (ID_Artist) REFERENCES Artist(ID_Artist),
  FOREIGN KEY (ID_Album) REFERENCES Album(ID_Album)
);
CREATE TABLE Playlist (
  ID_Playlist int NOT NULL PRIMARY KEY,
  ID_Acc int NULL,
  Name nvarchar(255) NOT NULL,
  Description nvarchar(255) NULL,
  Thumbnail varchar(255) NULL,
  FOREIGN KEY (ID_Acc) REFERENCES Account(ID_Acc)
);
CREATE TABLE PairSongArtist (
  ID_Song int NOT NULL,
  ID_Artist int NOT NULL,
  PRIMARY KEY (ID_Song, ID_Artist),
  FOREIGN KEY (ID_Song) REFERENCES Song(ID_Song),
  FOREIGN KEY (ID_Artist) REFERENCES Artist(ID_Artist)
);
CREATE TABLE PairSongPlaylist (
  ID_Song int NOT NULL,
  ID_Playlist int NOT NULL,
  PRIMARY KEY (ID_Song, ID_Playlist),
  FOREIGN KEY (ID_Song) REFERENCES Song(ID_Song),
  FOREIGN KEY (ID_Playlist) REFERENCES Playlist(ID_Playlist)
);
CREATE TABLE SearchHistory (
  Query nvarchar(255) NOT NULL,
  ID_Acc int NOT NULL,
  FOREIGN KEY (ID_Acc) REFERENCES Account(ID_Acc)
);




INSERT INTO Account(ID_Acc, Email, Name, Thumbnail, Password) VALUES (1, 'ttnam22@gmail.com', N'Trịnh Nam', 'https://myspotifyit1.000webhostapp.com/Image/account/001.jpg', '123456A')
INSERT INTO Account(ID_Acc, Email, Name, Thumbnail, Password) VALUES (2, 'chientran@gmail.com', N'Trần Chiến', 'https://myspotifyit1.000webhostapp.com/Image/local_playlist/001.jpg', '123456A')


insert into Album(ID_Album, Name, Description, Thumbnail) VALUES (1, N'Gấp', N'Album nhạc của Cá Hồi Hoang', 'https://myspotifyit1.000webhostapp.com/Image/album/Gap.png');
insert into Album(ID_Album, Name, Description, Thumbnail) VALUES (2, N'Qua Khung Cửa Sổ ', N'Album của Chillies', 'https://myspotifyit1.000webhostapp.com/Image/album/QuaKhungCuaSo.png');
insert into Album(ID_Album, Name, Description, Thumbnail) VALUES (3, N'Dealth Magnetic ', N'Death is here', 'https://myspotifyit1.000webhostapp.com/Image/album/DeathMagnetic.jpg');
insert into Album(ID_Album, Name, Description, Thumbnail) VALUES (4, N'Daily Mix 1', N'Daily Mix 1', 'https://myspotifyit1.000webhostapp.com/Image/album/QuaKhungCuaSo.png');
insert into Album(ID_Album, Name, Description, Thumbnail) VALUES (5, N'Daily Mix 2', N'Daily Mix 2', 'https://myspotifyit1.000webhostapp.com/Image/song/GheIuDau.jpg');

insert into Artist(ID_Artist, Name, Thumbnails) values (1, N'Metallica', 'https://myspotifyit1.000webhostapp.com/Image/artist/001.jpg');
insert into Artist(ID_Artist, Name, Thumbnails) values (2, N'Kendrick Lamar', 'https://myspotifyit1.000webhostapp.com/Image/artist/002.jpg');
insert into Artist(ID_Artist, Name, Thumbnails) values (3, N'tlinh', 'https://myspotifyit1.000webhostapp.com/Image/artist/tlinh.jpg');
insert into Artist(ID_Artist, Name, Thumbnails) values (4, N'Chillies', 'https://myspotifyit1.000webhostapp.com/Image/artist/004.jpg');
insert into Artist(ID_Artist, Name, Thumbnails) values (5, N'Cá Hồi Hoang', 'https://myspotifyit1.000webhostapp.com/Image/artist/cahoihoang.jpg');
insert into Artist(ID_Artist, Name, Thumbnails) values (6, N'Bức Tường', 'https://myspotifyit1.000webhostapp.com/Image/artist/005.jpg');
insert into Artist(ID_Artist, Name, Thumbnails) values (7, N'Lý Bực', 'https://myspotifyit1.000webhostapp.com/Image/artist/006.jpg');

insert into Author(ID_Author, Name, Thumbnail) values (1, N'Metallica', 'https://myspotifyit1.000webhostapp.com/Image/artist/001.jpg');
insert into Author(ID_Author, Name, Thumbnail) values (2, N'Kendrick Lamar', 'https://myspotifyit1.000webhostapp.com/Image/artist/002.jpg');
insert into Author(ID_Author, Name, Thumbnail) values (3, N'tlinh', 'https://myspotifyit1.000webhostapp.com/Image/artist/tlinh.jpg');
insert into Author(ID_Author, Name, Thumbnail) values (4, N'Chillies', 'https://myspotifyit1.000webhostapp.com/Image/artist/004.jpg');
insert into Author(ID_Author, Name, Thumbnail) values (5, N'Cá Hồi Hoang', 'https://myspotifyit1.000webhostapp.com/Image/artist/cahoihoang.jpg');
insert into Author(ID_Author, Name, Thumbnail) values (6, N'Lý Bực', 'https://myspotifyit1.000webhostapp.com/Image/artist/006.jpg');
insert into Author(ID_Author, Name, Thumbnail) values (7, N'Bức Tường', 'https://myspotifyit1.000webhostapp.com/Image/artist/005.jpg');


insert into Song(ID_Song, Thumbnail, URLmp3, Name, ViewCount, Description, Lyrics, ID_Album, ID_Artist, ID_Author) 
values(1, 'https://myspotifyit1.000webhostapp.com/Image/song/AllNightmareLong.jpg',
'https://myspotifyit1.000webhostapp.com/Song/AllNightmareLong.mp3', N'All Nightmare Long', 210, N'Just a nightmare', N'All nightmare long', 1, 1, 1);
insert into Song(ID_Song, Thumbnail, URLmp3, Name, ViewCount, Description, Lyrics, ID_Album, ID_Artist, ID_Author) 
values(2, 'https://myspotifyit1.000webhostapp.com/Image/song/ThatWasJustYourLife.jpg',
'https://myspotifyit1.000webhostapp.com/Song/ThatWasJustYourLife.mp3', 'That was just your life', 10, 'Just your life', 'That just your life', 1, 1, 1);
insert into Song(ID_Song, Thumbnail, URLmp3, Name, ViewCount, Description, Lyrics, ID_Album, ID_Artist, ID_Author) 
values(3, 'https://myspotifyit1.000webhostapp.com/Image/song/BrokenBeatAndScarred.jpg',
'https://myspotifyit1.000webhostapp.com/Song/BrokenBeatAndScarred.mp3', 'Broken Beat and Scarred', 10, 'Broken Beat and Scarred', 'Broken Beat and Scarred', 1, 1, 1);
insert into Song(ID_Song, Thumbnail, URLmp3, Name, ViewCount, Description, Lyrics, ID_Album, ID_Artist, ID_Author) 
values(4, 'https://myspotifyit1.000webhostapp.com/Image/song/TheDayThatNeverComes.jpg',
'https://myspotifyit1.000webhostapp.com/Song/TheDayThatNeverComes.mp3', 'The Day That Never Comes', 112, 'The Day That Never Comes', 'The Day That Never Comes', 1, 1, 1);
insert into Song(ID_Song, Thumbnail, URLmp3, Name, ViewCount, Description, Lyrics, ID_Album, ID_Artist, ID_Author) 
values(5, 'https://myspotifyit1.000webhostapp.com/Image/song/UnitedInGrief.jpg',
'https://myspotifyit1.000webhostapp.com/Song/UnitedInGrief.mp3', 'United In Grief', 90, 'Humble', 'Humble', 2, 2, 2);
insert into Song(ID_Song, Thumbnail, URLmp3, Name, ViewCount, Description, Lyrics, ID_Album, ID_Artist, ID_Author) 
values(6, 'https://myspotifyit1.000webhostapp.com/Image/song/N95.jpg',
'https://myspotifyit1.000webhostapp.com/Song/N95.mp3', 'N95', 90, 'N95', 'N95', 2, 2, 2);
insert into Song(ID_Song, Thumbnail, URLmp3, Name, ViewCount, Description, Lyrics, ID_Album, ID_Artist, ID_Author) 
values(7, 'https://myspotifyit1.000webhostapp.com/Image/song/WorldwideSteppers.jpg',
'https://myspotifyit1.000webhostapp.com/Song/WorldwideSteppers.mp3', 'Worldwide Steppers', 100, 'Worldwide Steppers', 'Worldwide Steppers', 2, 2, 2);
insert into Song(ID_Song, Thumbnail, URLmp3, Name, ViewCount, Lyrics, Description, ID_Album, ID_Artist, ID_Author) 
values(8, 'https://myspotifyit1.000webhostapp.com/Image/song/GheIuDau.jpg',
'https://myspotifyit1.000webhostapp.com/Song/GheIuDau.mp3', N'Ghệ iu dấu của em ơi', 200, N'ghệ iu dấu của em ơi
ghệ iu dấu của em ơi
ghệ có bik em cần ghệ
ghệ có muốn mình cặp kè?
oki hăm?
yêu không ép, tùy tâm
ốm đau sẽ bị chăm
kí hợp đồng mấy tỷ năm
ghệ iu dấu của em ơi
ghệ iu nhất hệ mặt trời
ghệ ơi muốn ghệ sang chơi
ghệ toàn khiến em bật cười
em có ghệ vui
yeah yeah có ghệ vui
em thích mỗi ghệ thui
ghệ ghệ ghệ ghệ ghệ ui
ghệ ơi trông ghệ xinh chưa
á-áo quần tinh tươm
trông cái mặt rất kháu đầy tinh khôn
rình được bé t-xinh thơm
em nhỏ nhắn và mi nhon
vẫn có ghệ khen ngon
hột xoàn với cả kim cương
nắm tay ghệ một phát em quên luôn
tình iu các cụ non
mama ghệ nghe đ u đã muốn có nụ con d u
tình iu ghệ ngày ngày cứ bự hơn
ghệ có muốn qua em quấn quít tít mù ôm nhau?
lần này em chắc chắn sẽ chậm hơn
không mắc lại lỗi, không ngẫn thị hơm
ghệ cứ liệu tự giác và cẩn thận
cứ, cứ liệu
ghệ iu dấu của em ơi
ghệ iu dấu của em ơi
ghệ có bik em cần ghệ
ghệ có muốn mình cặp kè?
oki hăm?
yêu không ép, tuỳ tâm
ốm đau sẽ bị chăm
kí hợp đồng mấy tỷ năm
ghệ iu dấu của em ơi
ghệ iu nhất hệ mặt trời
ghệ ơi muốn ghệ sang chơi
ghệ toàn khiến em bật cười
em có ghệ vui
yeah yeah có ghệ vui
em thích mỗi ghệ thui
ghệ ghệ ghệ ghệ ghệ ui
ghệ biết cách yêu em
những vết đau bên trong em, ghệ dần lấy đi hết
cảm xúc không tì vết
ghệ so dedicated, ghệ tâm lí phải biết
và có vấn đề thì ghệ giải quyết
bằng tình yêu và sự tử tế
với sự nâng niu và sự tinh tế
chưa có ai làm được như thế
cho nên
em sẽ
không hư đốn, chóng chán cả thèm
không đi tìm lại sự hỗn loạn mà em đã quen
danh tiếng và đồng tiền à, lãng xẹt
em chỉ muốn được bình yên và được ở cạnh ghệ
yeah yeah yeah yeah
em sẽ làm hết trong khả năng
chỉ cần là mình vẫn có thời gian', 'Worldwide Steppers', 3, 3, 3);

insert into Song(ID_Song, Thumbnail, URLmp3, Name, ViewCount, Lyrics, Description, ID_Album, ID_Artist, ID_Author) 
values(9, 'https://myspotifyit1.000webhostapp.com/Image/song/VungKyUc.jpg',
'https://myspotifyit1.000webhostapp.com/Song/VungKyUc.mp3', N'Vùng Ký Ức', 123, N'Trên phím đàn
Em bỏ lại ngày tháng bạc màu
Em bỏ lại nỗi nhớ ngày đầu
Em quên một câu nói
Đừng đi
Thêm chút đường
Ly đen dường như chẳng dịu lại
Như cung đàn đã hoá khờ dại
Chênh vênh một mình giữa tay ai
Vùng ký ức xưa ta còn nhau, còn đâu em hỡi?
Nhiều lần đã cố gắng quên đi dù cho
Tình mình đã vỡ đôi
Em tiếc nuối thêm làm chi?
Còn lại những giọt buồn trên mi
Mang những thanh âm kia cùng em đi
Gửi lại vùng ký ức ta trao về em
Một ngày đầy nắng
Nụ cười người mỗi lúc mây tan vào đêm
Một ngày người ghé thăm
Ngày mai nắng như nhạt hơn
Và ta thức dậy như đã lớn
Thôi giấc mơ trôi đi
Em có quên đôi khi một mai
Ta lỡ hẹn
Ánh mặt trời trên nóc toà nhà
Quay sang nhìn như hai người lạ
Đưa tay chào nhau cuối sân ga
Vùng ký ức xưa ta còn nhau, còn đâu em hỡi?
Nhiều lần đã cố gắng quên đi dù cho
Tình mình đã vỡ đôi
Em tiếc nuối thêm làm chi?
Còn lại những giọt buồn trên mi
Mang những thanh âm kia cùng em đi
Gửi lại vùng ký ức ta trao về em
Một ngày đầy nắng
Nụ cười người mỗi lúc mây tan vào đêm
Một ngày người ghé thăm
Và ngày mai nắng như nhạt hơn
Và ta thức dậy như đã lớn
Thôi giấc mơ trôi đi
Em có quên đôi khi một mai
Vùng ký ức xưa ta còn nhau...
Vùng ký ức xưa ta còn nhau, còn đâu em hỡi?
Nhiều lần đã cố gắng quên đi dù cho
Tình mình đã vỡ đôi
Em tiếc nuối thêm làm chi?
Còn lại những giọt buồn trên mi
Mang những thanh âm kia cùng em đi
Gửi lại vùng ký ức ta trao về em
Một ngày đầy nắng
Nụ cười người mỗi lúc mây tan vào đêm
Một ngày người ghé thăm
Và ngày mai nắng như nhạt hơn
Và ta thức dậy như đã lớn
Thôi giấc mơ trôi đi
Em có quên đôi khi một mai', 'Vung Ky Uc', 4, 4, 4);
insert into Song(ID_Song, Thumbnail, URLmp3, Name, ViewCount, Lyrics, Description, ID_Album, ID_Artist, ID_Author) 
values(10, 'https://myspotifyit1.000webhostapp.com/Image/song/BaoNhieu.jpg',
'https://myspotifyit1.000webhostapp.com/Song/BaoNhieu.mp3', N'Bao Nhiêu', 342, N'[Verse 1]
Có bao nhiêu người đến
Em đang chôn mình trong hàng trăm bức hình
Trong hàng trăm vô tình

Có bao nhiêu người đi
Vết thương khô cằn vắt trên mi
Có bao nhiêu người ở lại
Kiếm lý do để tồn tại

Có bao nhiêu lần say
Đêm nay em nằm chơ vơ trong cuộc tình
Không còn thấy chính mình

Có bao nhiêu lời ca
Viết cho linh hồn đã đi qua
Có bao nhiêu đang đợi chờ
Đếm theo hai kim từng giờ

[Chorus]
Tôi không muốn thấy em vội bước đi
Khi em chưa nói em đã nghĩ gì
Tôi không muốn đếm theo từng ngón tay
Khi trong tâm trí em luôn ở đấy

Tôi không muốn biết thêm điều dối gian
Nơi đây đã có bao nhiêu hoang tàn
Tôi không muốn chết thêm một lần, chết thêm một lần
Chết thêm một lần
', 'Bao Nhiêu', 4, 4, 4);
insert into Song(ID_Song, Thumbnail, URLmp3, Name, ViewCount, Lyrics, Description, ID_Album, ID_Artist, ID_Author) 
values(11, 'https://myspotifyit1.000webhostapp.com/Image/song/EmDungKhoc.jpg',
'https://myspotifyit1.000webhostapp.com/Song/EmDungKhoc.mp3', N'Em Đừng Khóc', 342, N'Tối em vội lên trên chuyến xe về nơi thiên đường
Mắt em chợt cay khi nhớ một người từng thương
Kính xe vừa thay áo mới sau cơn mưa rào
Tay em vội lau những xót xa trên má đào
Em đừng khóc
Nếu tình mình chưa thành hình hài
Em đừng khóc
Nếu mình hẹn không ngày gặp lại
Em đừng khóc
Nếu ta chưa thấy được cầu vồng sau nhiều cơn mưa
Em đừng khóc
Nếu một ngày không là nụ cười
Em đừng khóc
Nếu lòng mình đau vì một người
Em đừng khóc
Tối nay lại là đêm trắng em nằm yên trong phòng
Mắt em là mây xuôi đến chân trời hừng đông
Đếm theo 1 2 3 4 không còn ai và em nghe tim mệt nhoài
Tính xem thời gian em có bao nhiêu còn lại
Em đừng khóc
Nếu tình mình chưa thành hình hài
Em đừng khóc
Nếu mình hẹn không ngày gặp lại
Em đừng khóc
Nếu ta chưa thấy được cầu vồng sau nhiều cơn mưa
Em đừng khóc
Nếu một ngày không là nụ cười
Em đừng khóc
Nếu lòng mình đau vì một người
Em đừng khóc
Và em đừng khóc
(Em đừng, em đừng khóc)
(Em đừng, và em đừng khóc)
(Em đừng, em đừng khóc)
(Em đừng, và em đừng khóc)
Em đừng khóc
Nếu tình mình chưa thành hình hài
Em đừng khóc
Nếu mình hẹn không ngày gặp lại
Em đừng khóc
Nếu ta chưa thấy được cầu vồng sau nhiều cơn mưa
Em đừng khóc
Nếu một ngày không là nụ cười
Em đừng khóc
Nếu lòng mình đau vì một người
Em đừng khóc
Và em đừng khóc
Em đừng khóc
Nếu tình mình chưa thành hình hài
Em đừng khóc
Nếu mình hẹn không ngày gặp lại
Em đừng khóc
Nếu ta chưa thấy được cầu vồng sau nhiều cơn mưa
Em đừng khóc
Nếu một ngày không là nụ cười
Em đừng khóc
Nếu lòng mình đau vì một người
Em đừng khóc
Và em đừng khóc', 'Em Dung Khoc', 4, 4, 4);
insert into Song(ID_Song, Thumbnail, URLmp3, Name, ViewCount, Lyrics, Description, ID_Album, ID_Artist, ID_Author) 
values(12, 'https://myspotifyit1.000webhostapp.com/Image/song/GiaNhu.png',
'https://myspotifyit1.000webhostapp.com/Song/GiaNhu-Chillies.mp3', N'Giá Như', 432, N'Verse 1]
Giá như không phải nói một câu "giá như"
Trái tim này tặng em từ đầu được chứ?
Thấy những khung hình quay đều như cuốn phim
Thấy sao em buồn tênh lặng lẽ đi tìm

[Pre-Chorus]
Ta loay hoay giữa bốn bức tường
Ta như soi thân trong tấm gương kia thật lâu, thật sâu

[Chorus]
Xin em thôi về trong những giấc mơ đêm nay, hôn đôi môi lạnh giá
Về lại trong những khung hình giờ đã trôi rất xa
Đừng về trong những giấc mơ đêm nay
Khi đã lỡ đặt dấu chấm hết cho vơi đi, vơi nỗi đau còn lại khi đã không còn ai

[Verse 2]
Giống như con mèo hoang nhìn mây trắng bay
Giữa không trung tự do mình em ngồi đấy
Vẫn vuốt ve từng nỗi buồn em giấu đi
Giấu cho riêng mình em, để em mãi đi tìm

[Pre-Chorus]
Ta loay hoay giữa bốn bức tường
Ta như soi thân trong tấm gương kia thật lâu, thật sâu

[Chorus]
Xin em thôi về trong những giấc mơ đêm nay, hôn đôi môi lạnh giá
Về lại trong những khung hình giờ đã trôi rất xa
Đừng về trong những giấc mơ đêm nay
Khi đã lỡ đặt dấu chấm hết cho vơi đi, vơi nỗi đau còn lại khi đã không còn ai', 'Giá Như', 4, 4, 4);

select * from Song
insert into Song(ID_Song, Thumbnail, URLmp3, Name, ViewCount, Lyrics, Description, ID_Album, ID_Artist, ID_Author) 
values(13, 'https://myspotifyit1.000webhostapp.com/Image/song/Ms_May.jpg',
'https://myspotifyit1.000webhostapp.com/Song/Ms_May.mp3', 'Ms. May', 300, N'[Verse]
Và một chiều anh lang thang nơi góc phố ta hẹn hò
Anh bơ vơ giữa những chuyến xe vội vã
Từng ngày dài trôi qua anh đâu biết ta lạc nhau
Còn lại sau bao nhiêu tháng năm khiến ta vụn vỡ

Nhớ môi em, tay trong tay nắm chặt
Nhớ thân quen trôi theo từng ánh mắt đã xa
Đã biết thôi không yêu nhau nữa rồi
Vấn vương chi môi hôn hay là hơi ấm sớm mai

[Chorus]
Chỉ mình anh nơi này
Chẳng ai ở bên
Bàn tay cô đơn cần thêm những hơi ấm khi
Có em siết đôi vai gầy

Chỉ mình anh nơi này
Chẳng ai ở bên
Chiều mưa như ôi lạnh thêm anh vẫn nhớ đến
Tiếng yêu tháng năm êm đềm

[Rap: Magazine]
Áng mây vút bay, phố đông chuyến xe cứ đi
Có những cơn đau không phải đau nôm na căng da trên tứ chi
Có những cơn say ừ thì loay hoay những điều cũ kỹ
Mà quên mất là bi kịch trên đời bắt đầu đều bằng từ: bi
Chúng ta ghét giả dối nhưng lại chăm lo bề ngoài kỳ công
Có không giữ mất lại buồn, quá nhiều đêm dài lụy trông
Cũng thật lạ, mình cố gắng có thật nhiều tiền vì mong
Thay nhà, thay xe, thay cả một bộ đồ mới nhưng mà nỗi buồn thì không

Mà có khi phận duyên mình cũng tựa như lá nhỉ
Bước vội đến chóng đi mà lại chẳng kịp vội chào
Chuyện vui buồn của sau này vỏn vẹn đôi ta chỉ
Rụng về cội ôm ngày tháng hoặc bị gió cuốn đi nơi nao

Bước đi trước khi lệ hoen ướt mi lần cuối
Anh cũng ước có một ngày khác nơi đó hai ta vẫn tới
Một ngày mà em sẽ hỏi ai sẽ ôm đôi vai em?
Ừ thì anh im, nhưng nếu không phải anh, thì là ai em?

[Chorus]
Chỉ mình anh nơi này
Chẳng ai ở bên
Bàn tay cô đơn cần thêm những hơi ấm khi
Có em siết đôi vai gầy

Chỉ mình anh nơi này
Chẳng ai ở bên
Chiều mưa như ôi lạnh thêm anh vẫn nhớ đến
Tiếng yêu tháng năm êm đềm
Chỉ mình anh nơi này
Chẳng ai ở bên
Bàn tay cô đơn cần thêm những hơi ấm khi
Có em siết đôi vai gầy

Chỉ mình anh nơi này
Chẳng ai ở bên
Chiều mưa như ôi lạnh thêm anh vẫn nhớ đến
Tiếng yêu tháng năm êm đềm', 'May is May', 4, 4, 4);
insert into Song(ID_Song, Thumbnail, URLmp3, Name, ViewCount, Lyrics, Description, ID_Album, ID_Artist, ID_Author) 
values(14, 'https://myspotifyit1.000webhostapp.com/Image/song/MongDu.jpg',
'https://myspotifyit1.000webhostapp.com/Song/MongDu.mp3', N'Mộng Du', 241, N'[Verse 1]
Có bao giờ
Em bật khóc trong vô vọng
Từng lời nói như con dao
Nhắc em đừng vô tư
Tiếc chi câu chối từ

Nhớ như in
Từng con số trong danh bạ
Từng lời hứa đã đi qua
Còn đâu khi thời gian
Đưa ta vào trong ngày hôm qua

[Chorus]
Đừng chờ nữa nhé
Ngày tình đem xé đôi
Những trái tim chìm trong sương mù
Muốn thoát ra khỏi cơn mộng du

Đừng chờ nữa nhé
Một điều gì khác hơn
Những áng mây dần bay qua đầu
Những nhớ thương từng trao về nhau
Với cánh tay em ôm thân này
Giữa thế gian và những vòng xoay vơi đầy

[Verse 2]
Có đôi lần
Em gục ngã sau tiếng cười
Đời còn mấy khi đôi mươi
Những đêm dài lê thê
Bước chân tìm lối về

[Chorus]
Đừng chờ nữa nhé
Ngày tình đem xé đôi
Những trái tim chìm trong sương mù
Muốn thoát ra khỏi cơn mộng du

Đừng chờ nữa nhé
Một điều gì khác hơn
Những áng mây dần bay qua đầu
Những nhớ thương từng trao về nhau
Với cánh tay em ôm thân này
Giữa thế gian và những vòng xoay

[Bridge]
Đây là đâu và ta là ai lạc trong guồng quay
Những nghĩ suy chẳng thể nói hết
Xoay và xoay đều đêm ngày tan thành mây và ta
Như cánh chim tìm nơi để chết
[Chorus]
Đừng chờ nữa nhé
Ngày tình đem xé đôi
Những trái tim chìm trong sương mù
Muốn thoát ra khỏi cơn mộng du

Đừng chờ nữa nhé
Một điều gì khác hơn
Những áng mây dần bay qua đầu
Những nhớ thương từng trao về nhau

[Chorus]
Đừng chờ nữa nhé
Ngày tình đem xé đôi
Những trái tim chìm trong sương mù
Muốn thoát ra khỏi cơn mộng du

Đừng chờ nữa nhé
Một điều gì khác hơn
Những áng mây dần bay qua đầu
Những nhớ thương từng trao về nhau
Với cánh tay em ôm thân này
Giữa thế gian và những vòng xoay

[Chorus]
Đừng chờ nữa nhé
Ngày tình đem xé đôi
Những trái tim chìm trong sương mù
Muốn thoát ra khỏi cơn mộng du
Đừng chờ nữa nhé
Một điều gì khác hơn
Những áng mây dần bay qua đầu
Những nhớ thương từng trao về nhau', 'Như một cơn mơ ', 4, 4, 4);

insert into Song(ID_Song, Thumbnail, URLmp3, Name, ViewCount, Lyrics, Description, ID_Album, ID_Artist, ID_Author) 
values(15, 'https://myspotifyit1.000webhostapp.com/Image/song/QuaKhungCuaSo.jpg',
'https://myspotifyit1.000webhostapp.com/Song/QuaKhungCuaSo.mp3', N'Qua Khung Cửa Sổ', 352, N'[Verse 1]
Không ai đúng không ai sai
Không tiếng khóc trên mi ai
Không những thứ tha, van nài
Sao còn chưa thức dậy

Ta cứ đếm sao trên đầu
Tên em trước, ta đi sau
Vẫn cứ nối đuôi theo nhau

Như đoàn tàu lăn bánh
Đưa hồn ai theo gió
Đi xa khắp muôn nơi
Đi đến cuối chân trời

[Chorus 1]
Chờ đợi điều gì trong nỗi nhớ
Muốn em quay về đây
Giấc mơ thôi đừng bay
Vuốt ve nhiều đêm trái tim này
Mà nào em có hay

Bánh xe tên thời gian
Cứ xoay và vỡ tan
Những nốt nhạc yêu dấu qua khung cửa sổ
Đi trốn thế gian

[Verse 2]
Không sao đâu, anh đây rồi
Gió vẫn hát nơi ta ngồi
Bao yêu dấu vẫn trên môi

Như còn đang vương vấn
Bao lời yêu đã mất
Sao ta mãi ngu ngơ
Đêm qua vẫn mong chờ

[Chorus 1]
Chờ đợi điều gì trong nỗi nhớ
Muốn em quay về đây
Giấc mơ thôi đừng bay
Vuốt ve nhiều đêm trái tim này
Mà nào em có hay

Bánh xe tên thời gian
Cứ xoay và vỡ tan
Những nốt nhạc yêu dấu qua khung cửa sổ
Đi trốn thế gian

[Chorus 2]
Chờ đợi điều gì trong nỗi nhớ
Muốn em quay về đây
Giấc mơ thôi đừng bay
Vuốt ve nhiều đêm trái tim này
Mà nào em có hay
Bánh xe tên thời gian
Cứ xoay và vỡ tan
Những nốt nhạc yêu dấu đã mất
Giấu theo sau những giấc mơ

Muốn em quay về đây
Giấc mơ thôi đừng bay
Vuốt ve nhiều đêm trái tim này
Mà nào em có hay

Bánh xe tên thời gian
Cứ xoay và vỡ tan
Những nốt nhạc yêu dấu qua khung cửa sổ
Đi trốn thế gian

Qua khung cửa sổ
Qua khung cửa sổ đi trốn thế gian', 'Bên ngoài cánh cửa sổ', 4, 4, 4);
insert into Song(ID_Song, Thumbnail, URLmp3, Name, ViewCount, Lyrics, Description, ID_Album, ID_Artist, ID_Author) 
values(16, 'https://myspotifyit1.000webhostapp.com/Image/song/MotCaiTen.jpg',
'https://myspotifyit1.000webhostapp.com/Song/MotCaiTen.mp3', N'Một Cái Tên', 435, N'[Verse 1: Duy Khang]
Phía xa mình
Nhìn nhau và không nói câu gì
Vì sau đêm nay chúng ta
Bước theo hai lối ra

Cứ im lặng ngồi đây
Và ly whisky không còn say
Như em với ta
Chờ những âm thanh vỡ ra

[Chorus: Duy Khang]
Và ngày còn chờ ai với ai
Ngọt ngào này còn theo đến tai
Nguyện thề này còn ai giữ mãi
Khi em ở trong vòng tay của một ai khác đêm nay

Và dường như đã quên
Điệu nhạc buồn này ai cất lên
Để lại một hành tinh đã chết
Theo đêm tàn trên đầu môi em thôi nhung nhớ một cái tên

[Verse 2: Orange]
Có bao nhiêu lần đau
Thì ta cũng không là của nhau
Như lời dối gian
Đừng nhắc cho tim vỡ tan

Có bao nhiêu lần say
Thì anh cũng không quay về đây
Mong chờ nữa chi
Mình uống cho quên hết đi

[Chorus: Duy Khang, Orange]
Và ngày còn chờ ai với ai
Ngọt ngào này còn theo đến tai
Nguyện thề này còn ai giữ mãi
Khi em ở trong vòng tay của một ai khác đêm nay

Và dường như đã quên
Điệu nhạc buồn này ai cất lên
Để lại một hành tinh đã chết
Theo đêm tàn trên đầu môi em thôi nhung nhớ một cái tên

[Bridge: Duy Khang, Orange]
Vậy đây là đêm cuối bên nhau
Với những đắm say
Đừng chờ đợi gì vết thương sâu
Chẳng thể níu tay

Ngày qua mình có thấy vui hơn
Anh cứ bước đi
Và một lần để cho trái tim đau
Trái tim đau
[Chorus: Duy Khang, Orange]
Và ngày còn chờ ai với ai
Ngọt ngào này còn theo đến tai
Nguyện thề này còn ai giữ mãi
Khi em ở trong vòng tay của một ai khác đêm nay

Và dường như đã quên
Điệu nhạc buồn này ai cất lên
Để lại một hành tinh đã chết
Theo đêm tàn trên đầu môi em thôi nhung nhớ một cái tên', 'Một Cái Tên', 4, 4, 4);
insert into Song(ID_Song, Thumbnail, URLmp3, Name, ViewCount, Lyrics, Description, ID_Album, ID_Artist, ID_Author) 
values(17, 'https://myspotifyit1.000webhostapp.com/Image/song/Mascara.jpg',
'https://myspotifyit1.000webhostapp.com/Song/Mascara.mp3', 'Mascara', 371, N'[Verse 1]
Câu tạm biệt em nói trên môi
Anh biết đây là đêm cuối bên nhau mà thôi
Nhìn lại từng khoảnh khắc từng tồn tại
Ta từng khờ dại

Mắt em nhoà đi mascara
Em trách anh không đi đến nơi gọi là nhà
Dù vạn ngày cũng chẳng để lại gì
Tim mình thầm thì

[Chorus]
Mình còn lại gì ngoài cuộc gọi hai giờ đêm
Mình còn lại gì ngoài ngày dường như dài thêm
Nụ cười mà ta vô tình bỏ lại trên môi em sớm mai
Giờ xa khuất

Mình còn lại gì ngoài ngàn lời yêu đã trao?
Mình còn lại gì ngoài tình đã phai từ hôm nào?
Lại một ngày trôi đi về mây ngàn, em đi cùng đêm tàn
Mờ sương khói

[Verse 2]
Câu tạm biệt em nói trên môi
Anh biết đây là đêm cuối ta say mà thôi
Nhìn lại từng khoảnh khắc từng tồn tại
Ta từng khờ dại

Mắt em nhoà đi mascara
Em trách anh không đi đến nơi gọi là nhà
Dù vạn ngày cũng chẳng để lại gì
Tim mình thầm thì

[Chorus]
Mình còn lại gì ngoài cuộc gọi hai giờ đêm
Mình còn lại gì ngoài ngày dường như dài thêm
Nụ cười mà ta vô tình bỏ lại trên môi em sớm mai
Giờ xa khuất

Mình còn lại gì ngoài ngàn lời yêu đã trao
Mình còn lại gì ngoài tình đã phai từ hôm nào
Lại một ngày trôi đi về mây ngàn, em đi cùng đêm tàn
Mờ sương khói

[Chorus]
Mình còn lại gì ngoài cuộc gọi hai giờ đêm
Mình còn lại gì ngoài ngày dường như dài thêm
Nụ cười mà ta vô tình bỏ lại trên môi em sớm mai
Giờ xa khuất

Mình còn lại gì ngoài ngàn lời yêu đã trao
Mình còn lại gì ngoài tình đã phai từ hôm nào
Lại một ngày trôi đi về mây ngàn, em đi cùng đêm tàn
Mờ sương khói
[Chorus]
Mình còn lại gì ngoài cuộc gọi hai giờ đêm
Mình còn lại gì ngoài ngày dường như dài thêm
Nụ cười mà ta vô tình bỏ lại trên môi em sớm mai
Giờ xa khuất

Mình còn lại gì ngoài ngàn lời yêu đã trao
Mình còn lại gì ngoài tình đã phai từ hôm nào
Lại một ngày trôi đi về mây ngàn, em đi cùng đêm tàn
Mờ sương khói

[Outro]
Câu tạm biệt em nói trên môi
Anh biết đây là đêm cuối ta say mà thôi
Nhìn lại từng khoảnh khắc từng tồn tại
Ta từng khờ dại', 'Mascara for youuuu', 4, 4, 4);
insert into Song(ID_Song, Thumbnail, URLmp3, Name, ViewCount, Lyrics, Description, ID_Album, ID_Artist, ID_Author) 
values(18, 'https://myspotifyit1.000webhostapp.com/Image/song/DuongChanTroi.jpg',
'https://myspotifyit1.000webhostapp.com/Song/DuongChanTroi.mp3', N'Đường Chân Trời', 682, N'Em nhìn anh
Đem yêu dấu kia chôn vào đôi mắt
Quên thời gian
Xe lăn bánh ta quên ngày trôi nhanh

Chiều hoàng hôn trên mái tóc
Vai kề vai
Ta quên những sớm mai
Lại gần trao nhau
Những chiếc hôn khờ dại

Đến những chân trời em và anh
Đến những con đường quanh đồi xanh
Bỏ những ưu tư trong đời
Sau lưng ta cần chi
Cùng đến những thiên đường ta cuồng si

Ver 2:

Len vào tim
Em như giấc mơ tôi từng ôm ấp
Đêm từng đêm
Đan vào những ngón tay đầy hơi ấm

Chiều hoàng hôn trên mái tóc
Vai kề vai
Ta quên những sớm mai
Lại gần trao nhau
Những chiếc hôn khờ dại

Đến những chân trời em và anh
Đến những con đường quanh đồi xanh
Bỏ những ưu tư trong đời
Sau lưng ta cần chi
Cùng đến những thiên đường ta cuồng si

Bridge:

Một lần ta như quên thời gian
Ooh!
Và một lần ta như chìm trong ánh dương tàn
Em ơi gần lại đây thôi
Thêm yêu thương gửi ngày êm trôi
Cùng ánh hoàng hôn dần buông
Nơi môi tìm môi

Chorus:

Đến những chân trời em và anh
Đến những con đường quanh đồi xanh
Đến những con đường, con đường ấy
Bỏ những ưu tư trong đời
Sau lưng ta cần chi em hỡi
Đến những thiên đường ta cuồng si

Đến những chân trời em và anh
Đến những chân trời em và anh
Đến những con đường quanh đồi xanh
Đến những con đường, con đường ấy
Bỏ những ưu tư trong đời
Sau lưng ta cần chi em hỡi
Đến những thiên đường ta cuồng si
Đến những thiên đường ta cuồng si

Đến những chân trời em và anh
Đến những con đường quanh đồi xanh
Bỏ những ưu tư trong đời
Sau lưng ta cần chi
Đến những thiên đường ta cuồng si', 'Chờ đợi tại đường chân trời', 4, 4, 4);
insert into Song(ID_Song, Thumbnail, URLmp3, Name, ViewCount, Description, Lyrics, ID_Album, ID_Artist, ID_Author) 
values(19, 'https://myspotifyit1.000webhostapp.com/Image/song/TangThuong102.jpg',
'https://myspotifyit1.000webhostapp.com/Song/Tang-Thuong-102-Ca-Hoi-Hoang.mp3', N'Tầng thượng 102', 280, '', N'Nhìn mây bay
Bay đến khu rừng già
Nhìn ánh sáng trốn phía sau tòa nhà
Ngày trôi qua nhanh
Hay ngày còn chưa tới
Ngày chờ đợi ai về
Đến đây đón em về
Rồi trong đêm
Ta thấy cả bầu trời
Và ngày đó ta sẽ thôi chờ đợi
Ngày trôi qua nhanh
Hay ngày còn chưa tới
Ngày chờ đợi ai về
Đến đây đón em về
Nhìn xung quanh
Ai cũng đang thật vội
Nhìn sau lưng
Ai đã đi thật rồi
Ngày trôi qua nhanh
Hay ngày còn chưa tới
Ngày chờ đợi ai về
Đến đây đón em về
Ngày trôi qua nhanh
Hay ngày còn chưa tới
Ngày chờ đợi ai về
Đến đây đón em về', 3, 5, 5);
insert into Song(ID_Song, Thumbnail, URLmp3, Name, ViewCount, Description, Lyrics, ID_Album, ID_Artist, ID_Author) 
values(20, 'https://myspotifyit1.000webhostapp.com/Image/song/Gap.jpg',
'https://myspotifyit1.000webhostapp.com/Song/DungBuon.mp3', N'Đừng buồn', 280, '', N'Tôi mơ thấy mình thoả hiệp với một tên khốn cùng vài ba nụ cười vô hồn vô hồn
Liếc mắt nhìn về bên trái còn lại gì còn được gì để mà sai cả chông gai cũng mòn
Thức dậy trong sợ hãi đếm từng giây và mong ngày mai
Mình sẽ không bao giờ trở nên như thế
Nếu một ngày
Mọi thứ tôi từng nói đều gió bay
Như một thời
Sẽ không ai hiểu không ai chờ đợi
Nhưng xin em đừng lấy thế làm buồn lấy thế làm buồn
Nhưng xin em đừng lấy thế làm buồn lấy thế làm buồn
Tôi mơ thấy mình lặng lẽ lượn lờ quanh phố thẫn thờ bên ly cà phê một sớm mai
Chớp mắt nhìn về ngày sống vì yêu đương như điên và lòng trắc ẩn không cần thiết
Thức dậy trong sợ hãi đếm từng giây và mong ngày mai
Mình sẽ không bao giờ trở nên như thế (sẽ không bao giờ)
Nếu một ngày
Mọi thứ tôi từng nói đều gió bay
Như một thời
Sẽ không ai hiểu không ai chờ đợi
Nhưng xin em đừng lấy thế làm buồn lấy thế làm buồn ye
Nhưng xin em đừng lấy thế làm buồn lấy thế làm buồn yeh
Tôi mơ thấy mình đang sống ở thời trẻ thơ như đang bay trên mây cùng ước mơ
Bên cạnh loài người đảo điên trong guồng quay ngày xưa làm sao làm sao có thể thấy
Thức dậy trong sợ hãi đếm từng giây và nếu ngày mai
Nếu ngày mai mình trở nên như thế
Nếu một ngày
Mọi thứ tôi từng nói đều gió bay
Như một thời
Sẽ không ai hiểu không ai chờ đợi
Nhưng xin em đừng', 3, 5, 5);

insert into PairSongArtist(ID_Artist, ID_Song) values (1, 1)
insert into PairSongArtist(ID_Artist, ID_Song) values (1, 2)
insert into PairSongArtist(ID_Artist, ID_Song) values (1, 3)
insert into PairSongArtist(ID_Artist, ID_Song) values (1, 4)
insert into PairSongArtist(ID_Artist, ID_Song) values (2, 5)
insert into PairSongArtist(ID_Artist, ID_Song) values (2, 6)
insert into PairSongArtist(ID_Artist, ID_Song) values (2, 7)
insert into PairSongArtist(ID_Artist, ID_Song) values (5, 19)
insert into PairSongArtist(ID_Artist, ID_Song) values (5, 20)
insert into PairSongArtist(ID_Artist, ID_Song) values (3, 8)
insert into PairSongArtist(ID_Artist, ID_Song) values (4, 9)
insert into PairSongArtist(ID_Artist, ID_Song) values (4, 10)
insert into PairSongArtist(ID_Artist, ID_Song) values (4, 11)
insert into PairSongArtist(ID_Artist, ID_Song) values (4, 12)
insert into PairSongArtist(ID_Artist, ID_Song) values (4, 13)
insert into PairSongArtist(ID_Artist, ID_Song) values (4, 14)
insert into PairSongArtist(ID_Artist, ID_Song) values (4, 15)
insert into PairSongArtist(ID_Artist, ID_Song) values (4, 16)
insert into PairSongArtist(ID_Artist, ID_Song) values (4, 17)
insert into PairSongArtist(ID_Artist, ID_Song) values (4, 18)

select * from Song where ID_Album = 2
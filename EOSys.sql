CREATE DATABASE EOSys
GO
USE [EOSys]
GO
/****** Object:  Table [dbo].[BaiHoc]    Script Date: 4/18/2023 10:17:51 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BaiHoc](
	[MaBH] [int] IDENTITY(1,1) NOT NULL,
	[TenBH] [nvarchar](255) NULL,
	[MaChuong] [int] NULL,
	[Link] [nvarchar](100) NULL,
 CONSTRAINT [PK_ChiTietChuong] PRIMARY KEY CLUSTERED 
(
	[MaBH] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[CauHoi]    Script Date: 4/18/2023 10:17:51 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[CauHoi](
	[MaCH] [int] IDENTITY(1,1) NOT NULL,
	[MaChuong] [int] NULL,
	[TenCH] [nvarchar](255) NULL,
	[PA1] [nvarchar](255) NULL,
	[PA2] [nvarchar](255) NULL,
	[PA3] [nvarchar](255) NULL,
	[PA4] [nvarchar](255) NULL,
	[DapAn] [nvarchar](255) NULL,
 CONSTRAINT [PK_CauHoi] PRIMARY KEY CLUSTERED 
(
	[MaCH] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ChiTietDeThi]    Script Date: 4/18/2023 10:17:51 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ChiTietDeThi](
	[IDCH] [int] IDENTITY(1,1) NOT NULL,
	[MaDeThi] [int] NULL,
	[MaCH] [int] NULL,
	[PALuaChon] [nvarchar](255) NULL,
 CONSTRAINT [PK_ChiTietDeThi] PRIMARY KEY CLUSTERED 
(
	[IDCH] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ChiTietKyThi]    Script Date: 4/18/2023 10:17:51 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ChiTietKyThi](
	[MaKyThi] [int] NOT NULL,
	[MaDeThi] [int] NOT NULL,
 CONSTRAINT [PK_ChiTietKyThi] PRIMARY KEY CLUSTERED 
(
	[MaKyThi] ASC,
	[MaDeThi] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Chuong]    Script Date: 4/18/2023 10:17:51 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Chuong](
	[MaChuong] [int] IDENTITY(1,1) NOT NULL,
	[TenChuong] [nvarchar](255) NULL,
	[MaKH] [int] NULL,
 CONSTRAINT [PK__Chuong__0D6A804C45540381] PRIMARY KEY CLUSTERED 
(
	[MaChuong] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[DeThi]    Script Date: 4/18/2023 10:17:51 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DeThi](
	[MaDeThi] [int] IDENTITY(1,1) NOT NULL,
	[TGBatDau] [datetime] NULL,
	[TGKetThuc] [datetime] NULL,
	[Diem] [float] NULL,
	[NgayTao] [date] NULL,
	[MaHV] [int] NULL,
 CONSTRAINT [PK_DeThi] PRIMARY KEY CLUSTERED 
(
	[MaDeThi] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[HocVien]    Script Date: 4/18/2023 10:17:51 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[HocVien](
	[MaHV] [int] IDENTITY(1,1) NOT NULL,
	[MaKH] [int] NOT NULL,
	[MaND] [nchar](7) NOT NULL,
 CONSTRAINT [PK_HocVien] PRIMARY KEY CLUSTERED 
(
	[MaHV] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[KhoaHoc]    Script Date: 4/18/2023 10:17:51 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[KhoaHoc](
	[MaKH] [int] IDENTITY(1,1) NOT NULL,
	[TenKH] [nvarchar](100) NULL,
	[HocPhi] [float] NOT NULL,
	[NgayKG] [date] NOT NULL,
	[GhiChu] [nvarchar](50) NULL,
	[MaNV] [nvarchar](50) NOT NULL,
	[NgayTao] [date] NOT NULL,
	[TongSoChuong] [int] NULL,
	[Hinh] [nvarchar](50) NULL,
	[Xoa] [bit] NULL,
 CONSTRAINT [PK_KhoaHoc] PRIMARY KEY CLUSTERED 
(
	[MaKH] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[KyThi]    Script Date: 4/18/2023 10:17:51 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[KyThi](
	[MaKyThi] [int] IDENTITY(1,1) NOT NULL,
	[MaKH] [int] NULL,
	[TenKT] [nvarchar](255) NULL,
	[MaNV] [varchar](10) NULL,
	[TGLamBai] [int] NULL,
	[TGMoDe] [smalldatetime] NULL,
	[TGDongDe] [smalldatetime] NULL,
	[TongSoCau] [int] NULL,
	[MatKhau] [nvarchar](20) NULL,
	[SoLanLam] [int] NULL,
	[MaChuong] [int] NULL,
 CONSTRAINT [PK__KyThi__1403DE9821D9859F] PRIMARY KEY CLUSTERED 
(
	[MaKyThi] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[NguoiDung]    Script Date: 4/18/2023 10:17:51 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[NguoiDung](
	[MaND] [nchar](7) NOT NULL,
	[MatKhau] [nvarchar](100) NULL,
	[HoTen] [nvarchar](50) NOT NULL,
	[NgaySinh] [date] NOT NULL,
	[GioiTinh] [bit] NOT NULL,
	[DienThoai] [nvarchar](50) NOT NULL,
	[Email] [nvarchar](50) NOT NULL,
	[GhiChu] [nvarchar](max) NULL,
	[MaNV] [nvarchar](50) NULL,
	[NgayDK] [date] NOT NULL,
	[Xoa] [bit] NULL,
 CONSTRAINT [PK_NguoiHoc] PRIMARY KEY CLUSTERED 
(
	[MaND] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[NhanVien]    Script Date: 4/18/2023 10:17:51 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[NhanVien](
	[MaNV] [nvarchar](50) NOT NULL,
	[MatKhau] [nvarchar](100) NOT NULL,
	[HoTen] [nvarchar](50) NOT NULL,
	[VaiTro] [bit] NOT NULL,
	[Xoa] [bit] NULL,
 CONSTRAINT [PK_QuanTri] PRIMARY KEY CLUSTERED 
(
	[MaNV] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[KhoaHoc] ADD  CONSTRAINT [DF_KhoaHoc_NgayTao]  DEFAULT (getdate()) FOR [NgayTao]
GO
ALTER TABLE [dbo].[KhoaHoc] ADD  CONSTRAINT [df_XoaKhoaHoc]  DEFAULT ((0)) FOR [Xoa]
GO
ALTER TABLE [dbo].[NguoiDung] ADD  CONSTRAINT [DF_NguoiHoc_GioiTinh]  DEFAULT ((0)) FOR [GioiTinh]
GO
ALTER TABLE [dbo].[NguoiDung] ADD  CONSTRAINT [DF_NguoiHoc_NgayDK]  DEFAULT (getdate()) FOR [NgayDK]
GO
ALTER TABLE [dbo].[NguoiDung] ADD  CONSTRAINT [df_Xoa]  DEFAULT ((0)) FOR [Xoa]
GO
ALTER TABLE [dbo].[NhanVien] ADD  CONSTRAINT [DF_QuanTri_VaiTro]  DEFAULT ((0)) FOR [VaiTro]
GO
ALTER TABLE [dbo].[NhanVien] ADD  CONSTRAINT [df_XoaNV]  DEFAULT ((0)) FOR [Xoa]
GO
ALTER TABLE [dbo].[BaiHoc]  WITH CHECK ADD  CONSTRAINT [FK_BaiHoc_Chuong] FOREIGN KEY([MaChuong])
REFERENCES [dbo].[Chuong] ([MaChuong])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[BaiHoc] CHECK CONSTRAINT [FK_BaiHoc_Chuong]
GO
ALTER TABLE [dbo].[CauHoi]  WITH CHECK ADD  CONSTRAINT [FK_CauHoi_Chuong] FOREIGN KEY([MaChuong])
REFERENCES [dbo].[Chuong] ([MaChuong])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[CauHoi] CHECK CONSTRAINT [FK_CauHoi_Chuong]
GO
ALTER TABLE [dbo].[ChiTietDeThi]  WITH CHECK ADD  CONSTRAINT [FK_ChiTietDeThi_CauHoi] FOREIGN KEY([MaDeThi])
REFERENCES [dbo].[DeThi] ([MaDeThi])
GO
ALTER TABLE [dbo].[ChiTietDeThi] CHECK CONSTRAINT [FK_ChiTietDeThi_CauHoi]
GO
ALTER TABLE [dbo].[ChiTietDeThi]  WITH CHECK ADD  CONSTRAINT [FK_ChiTietDeThi_CauHoi1] FOREIGN KEY([MaCH])
REFERENCES [dbo].[CauHoi] ([MaCH])
GO
ALTER TABLE [dbo].[ChiTietDeThi] CHECK CONSTRAINT [FK_ChiTietDeThi_CauHoi1]
GO
ALTER TABLE [dbo].[ChiTietDeThi]  WITH CHECK ADD  CONSTRAINT [FK_ChiTietDeThi_DeThi] FOREIGN KEY([MaDeThi])
REFERENCES [dbo].[DeThi] ([MaDeThi])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[ChiTietDeThi] CHECK CONSTRAINT [FK_ChiTietDeThi_DeThi]
GO
ALTER TABLE [dbo].[ChiTietKyThi]  WITH CHECK ADD  CONSTRAINT [FK_ChiTietKyThi_DeThi] FOREIGN KEY([MaDeThi])
REFERENCES [dbo].[DeThi] ([MaDeThi])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[ChiTietKyThi] CHECK CONSTRAINT [FK_ChiTietKyThi_DeThi]
GO
ALTER TABLE [dbo].[ChiTietKyThi]  WITH CHECK ADD  CONSTRAINT [FK_ChiTietKyThi_KyThi] FOREIGN KEY([MaKyThi])
REFERENCES [dbo].[KyThi] ([MaKyThi])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[ChiTietKyThi] CHECK CONSTRAINT [FK_ChiTietKyThi_KyThi]
GO
ALTER TABLE [dbo].[Chuong]  WITH CHECK ADD  CONSTRAINT [FK_Chuong_KhoaHoc] FOREIGN KEY([MaKH])
REFERENCES [dbo].[KhoaHoc] ([MaKH])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Chuong] CHECK CONSTRAINT [FK_Chuong_KhoaHoc]
GO
ALTER TABLE [dbo].[DeThi]  WITH CHECK ADD  CONSTRAINT [FK_DeThi_Chuong] FOREIGN KEY([MaHV])
REFERENCES [dbo].[HocVien] ([MaHV])
GO
ALTER TABLE [dbo].[DeThi] CHECK CONSTRAINT [FK_DeThi_Chuong]
GO
ALTER TABLE [dbo].[HocVien]  WITH CHECK ADD  CONSTRAINT [FK_HocVien_KhoaHoc] FOREIGN KEY([MaKH])
REFERENCES [dbo].[KhoaHoc] ([MaKH])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[HocVien] CHECK CONSTRAINT [FK_HocVien_KhoaHoc]
GO
ALTER TABLE [dbo].[HocVien]  WITH CHECK ADD  CONSTRAINT [FK_HocVien_NguoiHoc] FOREIGN KEY([MaND])
REFERENCES [dbo].[NguoiDung] ([MaND])
ON UPDATE CASCADE
GO
ALTER TABLE [dbo].[HocVien] CHECK CONSTRAINT [FK_HocVien_NguoiHoc]
GO
ALTER TABLE [dbo].[KhoaHoc]  WITH CHECK ADD  CONSTRAINT [FK_KhoaHoc_NhanVien] FOREIGN KEY([MaNV])
REFERENCES [dbo].[NhanVien] ([MaNV])
GO
ALTER TABLE [dbo].[KhoaHoc] CHECK CONSTRAINT [FK_KhoaHoc_NhanVien]
GO
ALTER TABLE [dbo].[KyThi]  WITH CHECK ADD  CONSTRAINT [FK_KyThi_KhoaHoc] FOREIGN KEY([MaKyThi])
REFERENCES [dbo].[KyThi] ([MaKyThi])
GO
ALTER TABLE [dbo].[KyThi] CHECK CONSTRAINT [FK_KyThi_KhoaHoc]
GO
/****** Object:  StoredProcedure [dbo].[sp_BangDiemTheoKyThi]    Script Date: 4/18/2023 10:17:51 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROC [dbo].[sp_BangDiemTheoKyThi] (@MaKT INT)
AS BEGIN
	SELECT
		nd.MaND,
		nd.HoTen,
		dt.Diem,
		dt.MaDeThi,
		MaKyThi
	FROM HocVien hv
		 JOIN NguoiDung nd ON nd.MaND = hv.MaND
		 JOIN dbo.DeThi dt ON dt.MaHV = hv.MaHV
		 JOIN dbo.KyThi ON KyThi.MaKH = hv.MaKH
	WHERE MaKyThi = @MaKT
	ORDER BY dt.Diem DESC
END
GO
/****** Object:  StoredProcedure [dbo].[sp_CauHoiTheoKH]    Script Date: 4/18/2023 10:17:51 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROC [dbo].[sp_CauHoiTheoKH] (@MaKH INT, @MaChuong INT)
AS BEGIN
	SELECT
		ch.TenCH,
		ch.PA1,
		ch.PA2,
		ch.PA3,
		ch.PA4,
		ch.DapAn
	FROM Chuong c 
		JOIN dbo.CauHoi ch ON ch.MaChuong = c.MaChuong
	WHERE c.MaKH = @MaKH AND c.MaChuong = @MaChuong
	ORDER BY ch.TenCH
END
GO
/****** Object:  StoredProcedure [dbo].[sp_KyThiTheoKH]    Script Date: 4/18/2023 10:17:51 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROC [dbo].[sp_KyThiTheoKH] (@MaKH INT, @MaChuong INT)
AS BEGIN
	SELECT 
		kt.TenKT,
		nv.MaNV,
		kt.TGLamBai,
		kt.TGMoDe,
		kt.TGDongDe,
		kt.TongSoCau,
		kt.MatKhau,
		kt.SoLanLam,
		kt.MaChuong
	FROM dbo.KyThi kt
		JOIN dbo.NhanVien nv ON nv.MaNV = kt.MaNV
	WHERE kt.MaKH = @MaKH AND kt.MaChuong = @MaChuong
	ORDER BY kt.TenKT
END
GO
/****** Object:  StoredProcedure [dbo].[sp_LuongNguoiHocTheoKH]    Script Date: 4/18/2023 10:17:51 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROC [dbo].[sp_LuongNguoiHocTheoKH] (@MaKH INT)
AS BEGIN 
	SELECT 
		hv.MaND,
		nd.HoTen,
		nd.NgaySinh,
		nd.GioiTinh,
		nd.DienThoai,
		nd.Email
	FROM dbo.NguoiDung nd INNER JOIN dbo.HocVien hv ON hv.MaND = nd.MaND
	WHERE MaKH = @MaKH
	ORDER BY nd.MaND
END 
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Mã học viên, PK' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'HocVien', @level2type=N'COLUMN',@level2name=N'MaHV'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Mã khóa học, FK' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'HocVien', @level2type=N'COLUMN',@level2name=N'MaKH'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Mã người học, FK' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'HocVien', @level2type=N'COLUMN',@level2name=N'MaND'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Mã khách hàng, PK' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'KhoaHoc', @level2type=N'COLUMN',@level2name=N'MaKH'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Ngày khai giảng' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'KhoaHoc', @level2type=N'COLUMN',@level2name=N'NgayKG'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Ghi chú' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'KhoaHoc', @level2type=N'COLUMN',@level2name=N'GhiChu'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Mã người tạo, FK' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'KhoaHoc', @level2type=N'COLUMN',@level2name=N'MaNV'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Ngày tạo' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'KhoaHoc', @level2type=N'COLUMN',@level2name=N'NgayTao'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Mã người học, PK' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'NguoiDung', @level2type=N'COLUMN',@level2name=N'MaND'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Họ và tên' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'NguoiDung', @level2type=N'COLUMN',@level2name=N'HoTen'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Ngày sinh' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'NguoiDung', @level2type=N'COLUMN',@level2name=N'NgaySinh'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Giới tính' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'NguoiDung', @level2type=N'COLUMN',@level2name=N'GioiTinh'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Điện thoại' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'NguoiDung', @level2type=N'COLUMN',@level2name=N'DienThoai'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Địa chỉ email' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'NguoiDung', @level2type=N'COLUMN',@level2name=N'Email'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Ghi chú' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'NguoiDung', @level2type=N'COLUMN',@level2name=N'GhiChu'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Mã người tạo, FK' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'NguoiDung', @level2type=N'COLUMN',@level2name=N'MaNV'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Ngày tạo' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'NguoiDung', @level2type=N'COLUMN',@level2name=N'NgayDK'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Mã nhân viên, PK' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'NhanVien', @level2type=N'COLUMN',@level2name=N'MaNV'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Mật khẩu' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'NhanVien', @level2type=N'COLUMN',@level2name=N'MatKhau'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Họ và tên' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'NhanVien', @level2type=N'COLUMN',@level2name=N'HoTen'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Vai trò, 1-trưởng phòng' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'NhanVien', @level2type=N'COLUMN',@level2name=N'VaiTro'
GO

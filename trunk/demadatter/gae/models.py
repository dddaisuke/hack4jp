# -*- coding: utf-8 -*-

from google.appengine.ext import db


class Tweet(db.Model):
    """ツイート情報"""
    # ツイートID
    tweetid = db.StringProperty()
    # ツイート日時
    createdat = db.DateTimeProperty()
    # 本文
    tweet = db.StringProperty()
    # 発言者のスクリーンネーム
    screen_name = db.StringProperty()
    # 発言者のユーザ名
    user_name = db.StringProperty()
    # レポート件数
    count = db.IntegerProperty()


class User(db.Model):
    """ユーザ(正確には端末)情報"""
    # 生成(初回アクセス)日時
    date = db.DateTimeProperty(auto_now_add=True)
    # ユーザ確認トークン(ランダムキー)
    token = db.StringProperty()
    # トークンの長さ
    TOKEN_LENGTH = 20


class Report(db.Model):
    """レポート(デマ通報)"""
    # レポート日時
    date = db.DateTimeProperty(auto_now_add=True)
    # ユーザトークン
    token = db.StringProperty()
    # ツイートID
    tweetid = db.StringProperty()


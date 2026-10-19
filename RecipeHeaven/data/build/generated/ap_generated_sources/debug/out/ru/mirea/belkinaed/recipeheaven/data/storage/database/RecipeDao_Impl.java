package ru.mirea.belkinaed.recipeheaven.data.storage.database;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class RecipeDao_Impl implements RecipeDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<RecipeEntity> __insertionAdapterOfRecipeEntity;

  private final EntityDeletionOrUpdateAdapter<RecipeEntity> __updateAdapterOfRecipeEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteRecipeById;

  public RecipeDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfRecipeEntity = new EntityInsertionAdapter<RecipeEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `recipes` (`id`,`name`,`weatherType`,`steps`,`userId`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final RecipeEntity entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        if (entity.getWeatherType() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getWeatherType());
        }
        if (entity.getSteps() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getSteps());
        }
        statement.bindLong(5, entity.getUserId());
      }
    };
    this.__updateAdapterOfRecipeEntity = new EntityDeletionOrUpdateAdapter<RecipeEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `recipes` SET `id` = ?,`name` = ?,`weatherType` = ?,`steps` = ?,`userId` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final RecipeEntity entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        if (entity.getWeatherType() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getWeatherType());
        }
        if (entity.getSteps() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getSteps());
        }
        statement.bindLong(5, entity.getUserId());
        statement.bindLong(6, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteRecipeById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM recipes WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public void insertRecipe(final RecipeEntity recipe) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfRecipeEntity.insert(recipe);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateRecipe(final RecipeEntity recipe) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfRecipeEntity.handle(recipe);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteRecipeById(final int id) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteRecipeById.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, id);
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfDeleteRecipeById.release(_stmt);
    }
  }

  @Override
  public RecipeEntity getRecipeById(final int id) {
    final String _sql = "SELECT * FROM recipes WHERE id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfWeatherType = CursorUtil.getColumnIndexOrThrow(_cursor, "weatherType");
      final int _cursorIndexOfSteps = CursorUtil.getColumnIndexOrThrow(_cursor, "steps");
      final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
      final RecipeEntity _result;
      if (_cursor.moveToFirst()) {
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        final String _tmpWeatherType;
        if (_cursor.isNull(_cursorIndexOfWeatherType)) {
          _tmpWeatherType = null;
        } else {
          _tmpWeatherType = _cursor.getString(_cursorIndexOfWeatherType);
        }
        final String _tmpSteps;
        if (_cursor.isNull(_cursorIndexOfSteps)) {
          _tmpSteps = null;
        } else {
          _tmpSteps = _cursor.getString(_cursorIndexOfSteps);
        }
        final int _tmpUserId;
        _tmpUserId = _cursor.getInt(_cursorIndexOfUserId);
        _result = new RecipeEntity(_tmpId,_tmpName,_tmpWeatherType,_tmpSteps,_tmpUserId);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<RecipeEntity> getAllRecipes() {
    final String _sql = "SELECT * FROM recipes";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfWeatherType = CursorUtil.getColumnIndexOrThrow(_cursor, "weatherType");
      final int _cursorIndexOfSteps = CursorUtil.getColumnIndexOrThrow(_cursor, "steps");
      final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
      final List<RecipeEntity> _result = new ArrayList<RecipeEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final RecipeEntity _item;
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        final String _tmpWeatherType;
        if (_cursor.isNull(_cursorIndexOfWeatherType)) {
          _tmpWeatherType = null;
        } else {
          _tmpWeatherType = _cursor.getString(_cursorIndexOfWeatherType);
        }
        final String _tmpSteps;
        if (_cursor.isNull(_cursorIndexOfSteps)) {
          _tmpSteps = null;
        } else {
          _tmpSteps = _cursor.getString(_cursorIndexOfSteps);
        }
        final int _tmpUserId;
        _tmpUserId = _cursor.getInt(_cursorIndexOfUserId);
        _item = new RecipeEntity(_tmpId,_tmpName,_tmpWeatherType,_tmpSteps,_tmpUserId);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<RecipeEntity> getRecipesByWeatherType(final String type) {
    final String _sql = "SELECT * FROM recipes WHERE weatherType = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (type == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, type);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfWeatherType = CursorUtil.getColumnIndexOrThrow(_cursor, "weatherType");
      final int _cursorIndexOfSteps = CursorUtil.getColumnIndexOrThrow(_cursor, "steps");
      final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
      final List<RecipeEntity> _result = new ArrayList<RecipeEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final RecipeEntity _item;
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        final String _tmpWeatherType;
        if (_cursor.isNull(_cursorIndexOfWeatherType)) {
          _tmpWeatherType = null;
        } else {
          _tmpWeatherType = _cursor.getString(_cursorIndexOfWeatherType);
        }
        final String _tmpSteps;
        if (_cursor.isNull(_cursorIndexOfSteps)) {
          _tmpSteps = null;
        } else {
          _tmpSteps = _cursor.getString(_cursorIndexOfSteps);
        }
        final int _tmpUserId;
        _tmpUserId = _cursor.getInt(_cursorIndexOfUserId);
        _item = new RecipeEntity(_tmpId,_tmpName,_tmpWeatherType,_tmpSteps,_tmpUserId);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<RecipeEntity> getRecipesByUserId(final int userId) {
    final String _sql = "SELECT * FROM recipes WHERE userId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfWeatherType = CursorUtil.getColumnIndexOrThrow(_cursor, "weatherType");
      final int _cursorIndexOfSteps = CursorUtil.getColumnIndexOrThrow(_cursor, "steps");
      final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
      final List<RecipeEntity> _result = new ArrayList<RecipeEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final RecipeEntity _item;
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        final String _tmpWeatherType;
        if (_cursor.isNull(_cursorIndexOfWeatherType)) {
          _tmpWeatherType = null;
        } else {
          _tmpWeatherType = _cursor.getString(_cursorIndexOfWeatherType);
        }
        final String _tmpSteps;
        if (_cursor.isNull(_cursorIndexOfSteps)) {
          _tmpSteps = null;
        } else {
          _tmpSteps = _cursor.getString(_cursorIndexOfSteps);
        }
        final int _tmpUserId;
        _tmpUserId = _cursor.getInt(_cursorIndexOfUserId);
        _item = new RecipeEntity(_tmpId,_tmpName,_tmpWeatherType,_tmpSteps,_tmpUserId);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}

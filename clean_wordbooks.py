#!/usr/bin/env python3
import json
import os
import re
import shutil
from datetime import datetime
from pathlib import Path

WORDBOOKS_DIR = Path("src/main/resources/wordbooks")
BACKUP_ROOT = Path("wordbooks_backup")

# 常见带音标的西文字符替换为普通英文
ACCENT_MAP = {
    "à": "a", "á": "a", "â": "a", "ã": "a", "ä": "a", "å": "a",
    "è": "e", "é": "e", "ê": "e", "ë": "e",
    "ì": "i", "í": "i", "î": "i", "ï": "i",
    "ò": "o", "ó": "o", "ô": "o", "õ": "o", "ö": "o", "ø": "o",
    "ù": "u", "ú": "u", "û": "u", "ü": "u",
    "ý": "y", "ÿ": "y",
    "À": "A", "Á": "A", "Â": "A", "Ã": "A", "Ä": "A", "Å": "A",
    "È": "E", "É": "E", "Ê": "E", "Ë": "E",
    "Ì": "I", "Í": "I", "Î": "I", "Ï": "I",
    "Ò": "O", "Ó": "O", "Ô": "O", "Õ": "O", "Ö": "O", "Ø": "O",
    "Ù": "U", "Ú": "U", "Û": "U", "Ü": "U",
    "Ý": "Y",
}

RE_WHITESPACE = re.compile(r"\s+")


def normalize_string(value: str) -> str:
    value = value.strip()
    value = RE_WHITESPACE.sub(" ", value)
    return "".join(ACCENT_MAP.get(ch, ch) for ch in value)


def clean_value(value):
    if isinstance(value, str):
        return normalize_string(value)
    if isinstance(value, dict):
        return clean_dict(value)
    if isinstance(value, list):
        cleaned = [clean_value(item) for item in value]
        cleaned = [item for item in cleaned if item not in (None, "", [], {})]
        return cleaned
    return value


def clean_dict(data: dict) -> dict:
    output = {}
    for key, value in data.items():
        cleaned = clean_value(value)
        if cleaned in (None, "", [], {}):
            continue
        output[key] = cleaned
    return output


def records_from_root(root):
    if isinstance(root, list):
        return root
    if isinstance(root, dict):
        for candidate in ["words", "list", "items", "data"]:
            if candidate in root and isinstance(root[candidate], list):
                return root[candidate]
        return [root]
    return []


def record_key(record):
    if not isinstance(record, dict):
        return None
    word = record.get("word") or record.get("content") or record.get("text")
    if isinstance(word, str):
        return word.strip().lower()
    return None


def dedupe_records(records):
    seen = set()
    result = []
    for record in records:
        key = record_key(record)
        if key is None:
            result.append(record)
            continue
        if key in seen:
            continue
        seen.add(key)
        result.append(record)
    return result


def clean_file(path: Path, backup_root: Path):
    print(f"处理文件: {path}")
    with path.open("r", encoding="utf-8") as f:
        data = json.load(f)

    original_records = records_from_root(data)
    if not original_records:
        print(f"  跳过：未识别为词条列表")
        return

    cleaned_records = []
    for record in original_records:
        if not isinstance(record, dict):
            continue
        cleaned = clean_dict(record)
        word_key = record_key(cleaned)
        if not word_key:
            continue
        cleaned_records.append(cleaned)

    cleaned_records = dedupe_records(cleaned_records)

    if isinstance(data, dict) and any(k in data for k in ["words", "list", "items", "data"]):
        for candidate in ["words", "list", "items", "data"]:
            if candidate in data and isinstance(data[candidate], list):
                data[candidate] = cleaned_records
                break
    else:
        data = cleaned_records

    backup_file = backup_root / path.name
    shutil.copy2(path, backup_file)
    with path.open("w", encoding="utf-8") as f:
        json.dump(data, f, ensure_ascii=False, indent=2)
        f.write("\n")
    print(f"  已备份到: {backup_file}")
    print(f"  清洗完成, 记录数: {len(cleaned_records)}")


def main():
    if not WORDBOOKS_DIR.exists():
        print(f"错误: 目录不存在: {WORDBOOKS_DIR}")
        return

    timestamp = datetime.now().strftime("%Y%m%d_%H%M%S")
    backup_dest = BACKUP_ROOT / timestamp
    backup_dest.mkdir(parents=True, exist_ok=True)

    json_files = sorted(WORDBOOKS_DIR.glob("*.json"))
    if not json_files:
        print("未找到任何 JSON 文件")
        return

    print(f"开始处理 {len(json_files)} 个 JSON 文件")
    for path in json_files:
        try:
            clean_file(path, backup_dest)
        except Exception as e:
            print(f"  处理失败: {path} -> {e}")

    print("全部完成。")
    print(f"备份目录: {backup_dest}")


if __name__ == "__main__":
    main()
